package org.labcabrera.sample.api.geo.application.cqrs.commands;

import org.labcabrera.sample.api.geo.domain.IdCardType;

import jakarta.validation.constraints.NotNull;

public record CreateCaseFolderCommand(

    @NotNull String name,

    @NotNull String firstSurname,

    String lastSurname,

    @NotNull IdCardType idCardType,

    @NotNull String idCardNumber

) {
}
