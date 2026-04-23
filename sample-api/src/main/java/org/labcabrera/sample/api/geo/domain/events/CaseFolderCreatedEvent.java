package org.labcabrera.sample.api.geo.domain.events;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.domain.UserInfo;

/**
 * Event emitted when a case folder is created.
 */
public record CaseFolderCreatedEvent(
    String id,
    UserInfo userInfo,
    LocalDateTime createdAt) {
}
