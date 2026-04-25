package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

/**
 * Event emitted when a {@link Province} is deleted.
 */
public record ProvinceDeletedEvent(
    String provinceId,
    String provinceName,
    LocalDateTime deletedAt){
}
