package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProvinceEntityMapper {

    Province toDomain(ProvinceEntity entity);

    @Mapping(target = "version", ignore = true)
    ProvinceEntity toEntity(Province domain);

}
