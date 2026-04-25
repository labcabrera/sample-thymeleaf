package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

/**
 * Event emitted when a {@link org.labcabrera.sample.api.geo.domain.Municipality} is created.
 */
public record MunicipalityCreatedEvent(
    String id,
    String name,
    LocalDateTime createdAt) {
}
