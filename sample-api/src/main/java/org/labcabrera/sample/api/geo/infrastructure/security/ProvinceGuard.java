package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.stereotype.Component;

@Component
public class ProvinceGuard implements Guard<Province> {

    @Override
    public void checkRead(Province domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkWrite(Province domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }
}
