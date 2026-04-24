package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryJpaRepository extends
    JpaRepository<CountryEntity, String>,
    JpaSpecificationExecutor<CountryEntity> {

    Optional<CountryEntity> findByIdAndNameIgnoreCase(String id, String name);

}
