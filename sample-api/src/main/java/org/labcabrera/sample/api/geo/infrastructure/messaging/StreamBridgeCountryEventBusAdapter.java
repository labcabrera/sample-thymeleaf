package org.labcabrera.sample.api.geo.infrastructure.messaging;

import org.labcabrera.sample.api.geo.application.ports.CountryEventBusPort;
import org.labcabrera.sample.api.geo.domain.events.CountryCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.CountryDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.CountryUpdatedEvent;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.infrastructure.messaging.kafka.StreamBridgeEventBusAdapter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StreamBridgeCountryEventBusAdapter extends StreamBridgeEventBusAdapter
    implements CountryEventBusPort {

    public StreamBridgeCountryEventBusAdapter(StreamBridge streamBridge, SecurityPort securityPort) {
        super(streamBridge, securityPort);
    }

    @Override
    public void publish(CountryCreatedEvent event) {
        streamBridge.send("countryCreated-out-0", event);
    }

    @Override
    public void publish(CountryUpdatedEvent event) {
        streamBridge.send("countryUpdated-out-0", event);
    }

    @Override
    public void publish(CountryDeletedEvent event) {
        streamBridge.send("countryDeleted-out-0", event);
    }
}