package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProvinceEntityMapper {

    @Mapping(source = "country.id", target = "countryId")
    Province toDomain(ProvinceEntity entity);

    @Mapping(target = "country", expression = "java(domain.getCountryId() == null ? null : new CountryEntity(domain.getCountryId(), null, null, null, null))")
    @Mapping(target = "version", ignore = true)
    ProvinceEntity toEntity(Province domain);

}
