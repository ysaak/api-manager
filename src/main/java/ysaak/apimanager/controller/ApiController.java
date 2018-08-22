package ysaak.apimanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ysaak.apimanager.dto.ApiDto;
import ysaak.apimanager.dto.ApiVersionDto;
import ysaak.apimanager.exception.EntityNotFoundException;
import ysaak.apimanager.exception.EntityInvalidFieldsException;
import ysaak.apimanager.model.Api;
import ysaak.apimanager.model.ApiVersion;
import ysaak.apimanager.service.ApiService;
import ysaak.apimanager.utils.CollectionUtils;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiDto> create(@RequestBody ApiDto data) throws EntityInvalidFieldsException {
        Api dataStored = apiService.create(convertToEntity(data));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(dataStored.getCode()).toUri();
        return ResponseEntity.created(location).body(convertToDto(dataStored));
    }

    @GetMapping("/")
    public Collection<ApiDto> findAll() {
        final Collection<Api> apiCollection = apiService.findAll();
        return apiCollection.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public ApiDto findById(@PathVariable String code) throws EntityNotFoundException {
        return convertToDto(apiService.findByCode(code));
    }

    @PutMapping("/{code}")
    public ApiDto update(@RequestBody ApiDto data, @PathVariable String code) throws EntityNotFoundException {
        Api dataStored = apiService.update(convertToEntity(data));
        return convertToDto(dataStored);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) throws EntityNotFoundException {
        apiService.delete(code);
    }

    @PostMapping("/{code}/version")
    public void registerVersion(@RequestBody ApiVersionDto versionDto, @PathVariable String code) throws EntityInvalidFieldsException, EntityNotFoundException {
        apiService.registerVersion(
                code,
                convertToVersionEntity(versionDto)
        );
    }

    @GetMapping("/{code}/version/{version}")
    public ApiVersionDto getVersion(@PathVariable String code, @PathVariable String version) throws EntityNotFoundException {
        return convertVersionToDto(
                apiService.findVersion(code, version)
        );
    }


    @DeleteMapping("/{code}/version/{version}")
    public void unregisterVersion(@PathVariable String code, @PathVariable String version) throws EntityNotFoundException {
        apiService.unregisterVersion(code, version);
    }

    private Api convertToEntity(ApiDto dto) {
        if (dto == null) {
            return null;
        }

        return new Api(
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }

    private ApiVersion convertToVersionEntity(ApiVersionDto dto) {
        return new ApiVersion(
                dto.getVersion(),
                dto.getUrl()
        );
    }

    private ApiDto convertToDto(Api entity) {
        ApiDto dto = new ApiDto(
                entity.getCode(),
                entity.getName(),
                entity.getDescription()
        );

        if (CollectionUtils.isNotEmpty(entity.getVersions())) {
            dto.setVersions(entity.getVersions().stream().map(this::convertVersionToDto).collect(Collectors.toSet()));
        }

        return dto;
    }

    private ApiVersionDto convertVersionToDto(ApiVersion version) {
        return new ApiVersionDto(version.getVersion(), version.getUrl());
    }
}
