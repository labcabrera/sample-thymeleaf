package org.labcabrera.sample.api.geo.application.cqrs.commands;

import org.labcabrera.sample.api.geo.domain.IdCard;

public record UpdateCaseFolderCommand(
    String caseFolderId,
    String name,
    String firstSurname,
    String lastSurname,
    IdCard idCard) {
}
