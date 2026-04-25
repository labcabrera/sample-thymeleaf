package org.labcabrera.sample.api.geo.infrastructure.security;

import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.stereotype.Component;

@Component
public class PostalCodeGuard implements Guard<PostalCode> {

    @Override
    public void checkRead(PostalCode domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkWrite(PostalCode domain, AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void checkCreate(AuthenticatedUser user) {
        // TODO Auto-generated method stub
    }

}
