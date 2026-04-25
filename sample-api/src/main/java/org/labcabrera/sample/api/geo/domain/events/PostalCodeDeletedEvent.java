package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

public record PostalCodeDeletedEvent(
    String postalCodeId,
    String code,
    String municipalityId,
    String provinceId,
    LocalDateTime deletedAt) {

}
