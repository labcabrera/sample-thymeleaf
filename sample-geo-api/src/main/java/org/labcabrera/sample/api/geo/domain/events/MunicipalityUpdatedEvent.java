package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

/**
 * Event emitted when a {@link org.labcabrera.sample.api.geo.domain.Municipality} is updated.
 */
public record MunicipalityUpdatedEvent(
    String municipalityId,
    LocalDateTime updatedAt) {
}
