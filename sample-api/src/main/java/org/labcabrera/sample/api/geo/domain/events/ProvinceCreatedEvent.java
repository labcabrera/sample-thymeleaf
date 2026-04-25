package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.domain.Province;

/**
 * Event emitted when a {@link Province} is created.
 */
public record ProvinceCreatedEvent(
    String id,
    String name,
    LocalDateTime createdAt) {

    public static ProvinceCreatedEvent of(Province province) {
        return new ProvinceCreatedEvent(province.getId(), province.getName(), province.getCreatedAt());
    }
}
