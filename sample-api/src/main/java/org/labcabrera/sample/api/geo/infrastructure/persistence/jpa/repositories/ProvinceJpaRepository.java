package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceJpaRepository extends
    JpaRepository<ProvinceEntity, String>,
    JpaSpecificationExecutor<ProvinceEntity> {

    Optional<ProvinceEntity> findByCodeIgnoreCase(String code);

    Optional<ProvinceEntity> findByNameIgnoreCase(String name);
}