package org.labcabrera.sample.api.shared.application;

import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;

public interface Guard<T> {

    void checkRead(T domain, AuthenticatedUser user);

    void checkWrite(T domain, AuthenticatedUser user);

    void checkCreate(AuthenticatedUser user);

}
