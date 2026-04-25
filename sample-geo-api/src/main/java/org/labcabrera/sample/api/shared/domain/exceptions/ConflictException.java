package org.labcabrera.sample.api.shared.domain.exceptions;

public class ConflictException extends DomainException {

    public ConflictException(String code, Object... args) {
        super(code, 409, args);
    }

}
