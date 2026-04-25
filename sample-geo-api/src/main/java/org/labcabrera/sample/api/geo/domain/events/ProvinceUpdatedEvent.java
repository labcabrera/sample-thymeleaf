package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.domain.Province;

/**
 * Event emitted when a {@link Province} is updated.
 */
public record ProvinceUpdatedEvent(
    String provinceId,
    String name,
    LocalDateTime updatedAt) {

    public static ProvinceCreatedEvent of(Province updated) {
        return new ProvinceCreatedEvent(updated.getId(), updated.getName(), updated.getCreatedAt());
    }
}
