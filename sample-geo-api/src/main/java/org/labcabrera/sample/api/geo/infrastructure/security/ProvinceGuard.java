package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProvinceGuard implements Guard<Province> {

    @Value("${app.security.geo-admin-role}")
    private String geoAdminRole;

    @Override
    public void checkWrite(Province domain, AuthenticatedUser user) {
        this.checkRole(user, geoAdminRole);
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        this.checkRole(user, geoAdminRole);
    }
}
