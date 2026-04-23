package org.labcabrera.sample.api.geo.domain.events;

import org.labcabrera.sample.api.geo.domain.UserInfo;

/**
 * Event emitted when a case folder is deleted.
 */
public record CaseFolderDeletedEvent(
    String caseFolderId,
    UserInfo userInfo) {
}
