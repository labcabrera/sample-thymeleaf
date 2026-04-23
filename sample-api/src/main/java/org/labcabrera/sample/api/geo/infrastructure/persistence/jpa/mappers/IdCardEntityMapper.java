package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.api.geo.domain.IdCard;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.IdCardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdCardEntityMapper {

    IdCard toDomain(IdCardEntity entity);

    IdCardEntity toEntity(IdCard domain);

}
