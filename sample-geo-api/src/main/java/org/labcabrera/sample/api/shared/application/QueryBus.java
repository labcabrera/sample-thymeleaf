package org.labcabrera.sample.api.shared.application;

public interface QueryBus {

    <R> R dispatch(Object query);

}
