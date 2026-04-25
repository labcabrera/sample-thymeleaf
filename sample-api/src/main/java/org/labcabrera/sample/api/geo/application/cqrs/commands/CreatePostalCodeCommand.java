package org.labcabrera.sample.api.geo.application.cqrs.commands;

import jakarta.validation.constraints.NotNull;

public record CreatePostalCodeCommand(
    @NotNull String code,
    String municipalityId,
    String provinceId) {
}
