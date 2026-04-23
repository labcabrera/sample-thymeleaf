package org.labcabrera.sample.api.shared.application;

public interface CommandBus {

    <R> R dispatch(Object command);

}
