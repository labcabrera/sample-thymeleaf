package org.labcabrera.sample.api.geo.application.ports;

public interface CaseFolderMetricPort {

    void incrementCaseFolderCreatedCounter();

    void incrementCaseFolderUpdatedCounter();

    void incrementCaseFolderDeletedCounter();

}
