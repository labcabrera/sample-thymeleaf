package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

/**
 * Event emitted when a {@link Province} is updated.
 */
public record ProvinceUpdatedEvent(
    String provinceId,
    LocalDateTime updatedAt) {
}
