package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

public record CountryUpdatedEvent(
    String countryId,
    LocalDateTime updatedAt) {
}
