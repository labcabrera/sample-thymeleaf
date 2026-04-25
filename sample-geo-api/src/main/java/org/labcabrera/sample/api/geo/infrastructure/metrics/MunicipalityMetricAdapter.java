package org.labcabrera.sample.api.geo.infrastructure.metrics;

import org.labcabrera.sample.api.geo.application.ports.MunicipalityMetricPort;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityMetricAdapter implements MunicipalityMetricPort {

    @Override
    public void incrementCreatedCounter() {
        // TODO Auto-generated method stub
    }

    @Override
    public void incrementUpdatedCounter() {
        // TODO Auto-generated method stub
    }

    @Override
    public void incrementDeletedCounter() {
        // TODO Auto-generated method stub
    }

}
