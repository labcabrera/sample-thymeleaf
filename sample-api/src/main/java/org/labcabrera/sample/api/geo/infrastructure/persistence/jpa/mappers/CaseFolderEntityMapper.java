package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.CaseFolder;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.CaseFolderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserInfoEntityMapper.class })
public interface CaseFolderEntityMapper {

    CaseFolder toDomain(CaseFolderEntity entity);

    @Mapping(target = "version", ignore = true)
    CaseFolderEntity toEntity(CaseFolder domain);

}
