package org.labcabrera.sample.api.geo.application.ports;

public interface CountryMetricPort {

    void incrementCreatedCounter();

    void incrementUpdatedCounter();

    void incrementDeletedCounter();

}
