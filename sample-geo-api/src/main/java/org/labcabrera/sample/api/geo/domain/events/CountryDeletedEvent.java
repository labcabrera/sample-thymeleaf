package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

public record CountryDeletedEvent(
    String countryId,
    String countryName,
    LocalDateTime deletedAt) {
}
