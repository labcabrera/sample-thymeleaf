package org.labcabrera.sample.api.geo.interfaces.http.mappers;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.ProvinceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceDtoMapper {

    ProvinceDto toDto(Province domain);

}