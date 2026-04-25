package org.labcabrera.sample.api.geo.application.cqrs.commands;

import jakarta.validation.constraints.NotNull;

public record UpdateCountryCommand(
    @NotNull String countryId,
    String name) {
}
