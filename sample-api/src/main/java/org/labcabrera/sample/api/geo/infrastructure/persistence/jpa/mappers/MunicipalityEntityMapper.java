package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.MunicipalityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipalityEntityMapper {

    Municipality toDomain(MunicipalityEntity entity);

    @Mapping(target = "version", ignore = true)
    MunicipalityEntity toEntity(Municipality domain);

}
