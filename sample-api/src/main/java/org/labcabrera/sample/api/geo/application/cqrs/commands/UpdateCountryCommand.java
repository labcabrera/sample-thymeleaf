package org.labcabrera.sample.api.geo.application.cqrs.commands;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public record UpdateCountryCommand(
    @NotNull String countryId,
    Optional<String> name) {
}
