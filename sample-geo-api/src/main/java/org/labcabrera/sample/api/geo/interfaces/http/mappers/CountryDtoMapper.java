package org.labcabrera.sample.api.geo.interfaces.http.mappers;

import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryDtoMapper {

    CountryDto toDto(Country domain);

}
