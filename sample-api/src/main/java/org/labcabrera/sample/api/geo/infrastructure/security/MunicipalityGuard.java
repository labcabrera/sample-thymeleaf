package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityGuard implements Guard<Municipality> {

    @Override
    public void checkRead(Municipality domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkWrite(Municipality domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }


}
