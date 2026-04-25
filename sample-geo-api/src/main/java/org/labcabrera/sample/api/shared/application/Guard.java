package org.labcabrera.sample.api.shared.application;

import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;

public interface Guard<T> {

    default void checkRead(T domain, AuthenticatedUser user) {
    }

    void checkWrite(T domain, AuthenticatedUser user);

    void checkCreate(AuthenticatedUser user);

    default void checkRole(AuthenticatedUser user, String role) {
        if (!user.hasRole(role)) {
            throw new SecurityException("User does not have required role: " + role);
        }
    }

}
