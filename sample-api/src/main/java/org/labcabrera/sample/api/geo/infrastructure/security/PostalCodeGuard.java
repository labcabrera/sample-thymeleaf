package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostalCodeGuard implements Guard<PostalCode> {

    @Value("${app.security.geo-admin-role}")
    private String geoAdminRole;

    @Override
    public void checkWrite(PostalCode domain, AuthenticatedUser user) {
        this.checkRole(user, geoAdminRole);
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        this.checkRole(user, geoAdminRole);
    }

}
