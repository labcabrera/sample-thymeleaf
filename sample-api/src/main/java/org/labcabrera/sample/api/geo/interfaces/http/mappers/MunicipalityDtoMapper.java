package org.labcabrera.sample.api.geo.interfaces.http.mappers;

import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.MunicipalityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityDtoMapper {

    MunicipalityDto toDto(Municipality domain);

}
