package org.labcabrera.sample.api.geo.application.cqrs.queries;

import jakarta.validation.constraints.NotNull;

public record GetPostalCodeByIdQuery(
    @NotNull String postalCodeId) {
}
