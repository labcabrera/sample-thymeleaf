package org.labcabrera.sample.api.geo.infrastructure.metrics;

import org.labcabrera.sample.api.geo.application.ports.PostalCodeMetricPort;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class PostalCodeMetricAdapter implements PostalCodeMetricPort {

    private final Counter createdCounter;
    private final Counter updatedCounter;
    private final Counter deletedCounter;

    public PostalCodeMetricAdapter(MeterRegistry meterRegistry) {
        createdCounter = Counter.builder("postalcode_created")
            .description("Number of postal codes created")
            .register(meterRegistry);
        updatedCounter = Counter.builder("postalcode_updated")
            .description("Number of postal codes updated")
            .register(meterRegistry);
        deletedCounter = Counter.builder("postalcode_deleted")
            .description("Number of postal codes deleted")
            .register(meterRegistry);
    }

    @Override
    public void incrementCreatedCounter() {
        createdCounter.increment();
    }

    @Override
    public void incrementUpdatedCounter() {
        updatedCounter.increment();
    }

    @Override
    public void incrementDeletedCounter() {
        deletedCounter.increment();
    }

}
