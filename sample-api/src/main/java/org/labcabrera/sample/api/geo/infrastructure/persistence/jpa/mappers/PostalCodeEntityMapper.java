package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.PostalCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostalCodeEntityMapper {

    @Mapping(source = "municipality.id", target = "municipalityId")
    PostalCode toDomain(PostalCodeEntity entity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "municipality", expression = "java(domain.getMunicipalityId() == null ? null : new MunicipalityEntity(domain.getMunicipalityId(), null, null, null, null, null, null))")
    PostalCodeEntity toEntity(PostalCode domain);

}
