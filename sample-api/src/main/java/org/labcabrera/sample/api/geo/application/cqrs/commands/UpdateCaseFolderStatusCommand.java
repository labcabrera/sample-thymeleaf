package org.labcabrera.sample.api.geo.application.cqrs.commands;

import org.labcabrera.sample.api.geo.domain.CaseFolderStatus;

public record UpdateCaseFolderStatusCommand(
    String caseFolderId,
    CaseFolderStatus status) {
}
