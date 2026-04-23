package org.labcabrera.sample.api.geo.domain.events;

import org.labcabrera.sample.api.geo.domain.UserInfo;

public record CaseFolderUpdatedEvent(
    String id,
    UserInfo userInfo) {
}
