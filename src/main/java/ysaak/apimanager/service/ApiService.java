package ysaak.apimanager.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ysaak.apimanager.exception.EntityInvalidFieldsException;
import ysaak.apimanager.exception.EntityNotFoundException;
import ysaak.apimanager.model.Api;
import ysaak.apimanager.model.ApiVersion;
import ysaak.apimanager.repository.ApiRepository;
import ysaak.apimanager.utils.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApiService {

    private final ApiRepository apiRepository;
    private final Validator beanValidator;

    @Autowired
    public ApiService(ApiRepository apiRepository, Validator beanValidator) {
        this.apiRepository = apiRepository;
        this.beanValidator = beanValidator;
    }

    public Api create(Api data) throws EntityInvalidFieldsException {
        validate(data);

        Optional<Api> apiWithSameCode = apiRepository.findByCode(data.getCode());

        if (apiWithSameCode.isPresent()) {
            throw new EntityInvalidFieldsException(Api.class, "code");
        }

        return apiRepository.save(data);
    }

    private <T> void validate(T data) throws EntityInvalidFieldsException {
        final Set<ConstraintViolation<T>> violationSet = beanValidator.validate(data);
        if (CollectionUtils.isNotEmpty(violationSet)) {
            throw new EntityInvalidFieldsException(data.getClass(), (Set) violationSet);
        }
    }

    public Api findByCode(final String code) throws EntityNotFoundException {
        return apiRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(Api.class, "code", code));
    }

    public Collection<Api> findAll() {
        final Iterable<Api> apiDbIterable = this.apiRepository.findAll();

        final List<Api> apiList = new ArrayList<>();
        apiDbIterable.forEach(apiList::add);

        return apiList;
    }

    public Api update(Api data) throws EntityNotFoundException {
        final Api existingApi = findByCode(data.getCode());

        data.setId(existingApi.getId());
        data.setVersions(existingApi.getVersions());

        return apiRepository.save(data);
    }

    public void delete(final String code) throws EntityNotFoundException {
        Api api = findByCode(code);
        apiRepository.delete(api);
    }

    public void registerVersion(String code, ApiVersion version) throws EntityNotFoundException, EntityInvalidFieldsException {
        Api api = findByCode(code);
        validate(version);


        version.setApi(api);
        if (api.getVersions() == null) {
            api.setVersions(new HashSet<>());
        }
        api.getVersions().add(version);

        apiRepository.save(api);
    }

    public ApiVersion findVersion(String code, String version) throws EntityNotFoundException {
        final Api api = findByCode(code);

        ApiVersion apiVersion = null;

        if (CollectionUtils.isNotEmpty(api.getVersions())) {
            // Remove * in requested version
            final int starIndex = version.indexOf('*');
            String requestedVersion = (starIndex > 0) ? version.substring(0, starIndex-1) : version;

            Optional<ApiVersion> versionOptional = api.getVersions().stream().filter(av -> av.getVersion().startsWith(requestedVersion)).min(Comparator.reverseOrder());

            if (versionOptional.isPresent()) {
                apiVersion = versionOptional.get();
            }
        }

        if (apiVersion == null) {
            throw new EntityNotFoundException(Api.class, "code", code, "version", version);
        }

        return apiVersion;
    }

    public void unregisterVersion(String code, String version) throws EntityNotFoundException {
        Api api = findByCode(code);

        if (CollectionUtils.isNotEmpty(api.getVersions())) {
            for (ApiVersion apiVersion : new ArrayList<>(api.getVersions())) {
                if (StringUtils.equals(apiVersion.getVersion(), version)) {
                    api.getVersions().remove(apiVersion);
                }
            }

            apiRepository.save(api);
        }
    }
}
