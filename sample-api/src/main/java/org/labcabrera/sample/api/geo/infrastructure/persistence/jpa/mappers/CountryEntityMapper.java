package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryEntityMapper {

    Country toDomain(CountryEntity entity);

    @Mapping(target = "version", ignore = true)
    CountryEntity toEntity(Country domain);

}
