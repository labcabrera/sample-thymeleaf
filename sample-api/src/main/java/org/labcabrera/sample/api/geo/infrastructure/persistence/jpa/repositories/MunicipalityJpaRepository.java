package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityJpaRepository  extends
    JpaRepository<MunicipalityEntity, String>,
    JpaSpecificationExecutor<MunicipalityEntity> {

    Optional<MunicipalityEntity> findByCodeIgnoreCase(String code);

    Optional<MunicipalityEntity> findByNameIgnoreCase(String name);
}