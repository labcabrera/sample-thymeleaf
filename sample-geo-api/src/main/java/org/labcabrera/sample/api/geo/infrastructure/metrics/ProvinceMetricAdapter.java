package org.labcabrera.sample.api.geo.infrastructure.metrics;

import org.labcabrera.sample.api.geo.application.ports.ProvinceMetricPort;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Implementation of {@link CaseFolderMetricPort} based on Micrometer.
 */
@Component
public class ProvinceMetricAdapter implements ProvinceMetricPort {

    private final Counter caseFolderCreatedCounter;
    private final Counter caseFolderUpdatedCounter;
    private final Counter caseFolderDeletedCounter;

    public ProvinceMetricAdapter(MeterRegistry meterRegistry) {
        caseFolderCreatedCounter = Counter.builder("province_created")
            .description("Number of provinces created")
            .register(meterRegistry);
        caseFolderUpdatedCounter = Counter.builder("province_updated")
            .description("Number of provinces updated")
            .register(meterRegistry);
        caseFolderDeletedCounter = Counter.builder("province_deleted")
            .description("Number of provinces deleted")
            .register(meterRegistry);
    }

    @Override
    public void incrementCreatedCounter() {
        caseFolderCreatedCounter.increment();
    }

    @Override
    public void incrementUpdatedCounter() {
        caseFolderUpdatedCounter.increment();
    }

    @Override
    public void incrementDeletedCounter() {
        caseFolderDeletedCounter.increment();
    }
}
