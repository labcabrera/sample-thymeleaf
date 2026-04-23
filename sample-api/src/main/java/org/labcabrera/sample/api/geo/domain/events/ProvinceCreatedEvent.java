package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

/**
 * Event emitted when a {@link Province} is created.
 */
public record ProvinceCreatedEvent(
    String id,
    String name,
    LocalDateTime createdAt) {
}
