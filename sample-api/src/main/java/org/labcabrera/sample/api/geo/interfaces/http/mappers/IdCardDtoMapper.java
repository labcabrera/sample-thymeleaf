package org.labcabrera.sample.api.geo.interfaces.http.mappers;

import org.labcabrera.sample.api.geo.domain.IdCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.labcabrera.sample.archetype.generated.model.IdCardDto;

@Mapper(componentModel = "spring")
public interface IdCardDtoMapper {

    @Mapping(source = "idCardType", target = "type")
    @Mapping(source = "idCardNumber", target = "number")
    IdCardDto toDto(IdCard domain);

}
