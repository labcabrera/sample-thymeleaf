package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProvinceEntityMapper {

    @Mapping(target = "countryId", source = "countryCode")
    Province toDomain(ProvinceEntity entity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "id")
    @Mapping(target = "countryCode", source = "countryId")
    ProvinceEntity toEntity(Province domain);

}
