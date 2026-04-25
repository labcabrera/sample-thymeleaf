package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

public record CountryCreatedEvent(
    String id,
    String name,
    LocalDateTime createdAt) {
}
