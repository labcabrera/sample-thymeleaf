package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

public record PostalCodeUpdatedEvent(
    String postalCodeId,
    String code,
    String provinceId,
    LocalDateTime updatedAt) {
}
