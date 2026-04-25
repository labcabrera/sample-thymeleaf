package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.PostalCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostalCodeEntityMapper {

    @Mapping(source = "province.id", target = "provinceId")
    PostalCode toDomain(PostalCodeEntity entity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "province", expression = "java(domain.getProvinceId() == null ? null : new ProvinceEntity(domain.getProvinceId(), null, null, null, null, null, null))")
    PostalCodeEntity toEntity(PostalCode domain);

}
