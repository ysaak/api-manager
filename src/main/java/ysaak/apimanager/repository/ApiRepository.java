package ysaak.apimanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.apimanager.model.Api;

import java.util.Optional;

@Repository
public interface ApiRepository extends CrudRepository<Api, Long> {
    Optional<Api> findByCode(String code);
}
