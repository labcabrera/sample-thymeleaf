package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.stereotype.Component;

@Component
public class CountryGuard implements Guard<Country> {

    @Override
    public void checkRead(Country domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkWrite(Country domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

}
