package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.MunicipalityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipalityEntityMapper {

    @Mapping(source = "province.id", target = "provinceId")
    Municipality toDomain(MunicipalityEntity entity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "province", expression = "java(domain.provinceId() == null ? null : new ProvinceEntity(domain.provinceId(), null, null, null, null, null, null))")
    MunicipalityEntity toEntity(Municipality domain);

}
