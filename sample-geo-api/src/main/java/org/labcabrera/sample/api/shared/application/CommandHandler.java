package org.labcabrera.sample.api.shared.application;

public interface CommandHandler<C, R> {

    R handle(C command);

}
