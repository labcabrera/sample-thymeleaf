package org.labcabrera.sample.api.geo.application.cqrs.commands;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public record UpdateMunicipalityCommand(
    @NotNull String municipalityId,
    Optional<String> name,
    Optional<String> provinceId) {
}
