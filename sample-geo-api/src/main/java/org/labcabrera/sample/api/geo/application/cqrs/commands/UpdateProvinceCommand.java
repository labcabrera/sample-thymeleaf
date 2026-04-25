package org.labcabrera.sample.api.geo.application.cqrs.commands;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public record UpdateProvinceCommand(
    @NotNull String provinceId,
    Optional<String> name,
    Optional<String> countryId) {
}
