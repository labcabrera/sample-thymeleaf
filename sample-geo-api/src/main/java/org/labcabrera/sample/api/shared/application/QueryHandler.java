package org.labcabrera.sample.api.shared.application;

public interface QueryHandler<Q, R> {

    R handle(Q query);

}
