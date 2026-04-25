package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.domain.PostalCode;

public record PostalCodeCreatedEvent(
    String postalCodeId,
    String code,
    String municipalityId,
    LocalDateTime createdAt) {

    public static PostalCodeCreatedEvent of(PostalCode postalCode) {
        return new PostalCodeCreatedEvent(
            postalCode.getId(),
            postalCode.getCode(),
            postalCode.getMunicipalityId(),
            postalCode.getCreatedAt());
    }
}
