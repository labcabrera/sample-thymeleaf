package org.labcabrera.sample.api.geo.interfaces.http.mappers;

import org.labcabrera.sample.api.geo.domain.CaseFolder;
import org.mapstruct.Mapper;

import com.labcabrera.sample.archetype.generated.model.CaseFolderDto;

@Mapper(componentModel = "spring", uses = { UserInfoDtoMapper.class })
public interface CaseFolderDtoMapper {

    CaseFolderDto toDto(CaseFolder domain);

}
