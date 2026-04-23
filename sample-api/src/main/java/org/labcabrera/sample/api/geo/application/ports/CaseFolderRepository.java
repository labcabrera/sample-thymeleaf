package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.CaseFolder;
import org.labcabrera.sample.api.geo.domain.CaseFolderStatus;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaseFolderRepository {

    Optional<CaseFolder> findById(String caseFolder);

    Page<CaseFolder> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    CaseFolder save(CaseFolder entity);

    CaseFolder update(String caseFolderId, CaseFolder updatedData);

    CaseFolder updateStatus(String caseFolderId, CaseFolderStatus status);

    void deleteById(String caseFolderId);

}