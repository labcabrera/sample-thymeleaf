package org.labcabrera.sample.api.geo.infrastructure.messaging;

import org.labcabrera.sample.api.geo.application.ports.PostalCodeEventBusPort;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeUpdatedEvent;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.infrastructure.messaging.kafka.StreamBridgeEventBusAdapter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class StreamBridgePostalCodeEventBusAdapter extends StreamBridgeEventBusAdapter
    implements PostalCodeEventBusPort {

    public StreamBridgePostalCodeEventBusAdapter(StreamBridge streamBridge, SecurityPort securityPort) {
        super(streamBridge, securityPort);
    }

    @Override
    public void publish(PostalCodeCreatedEvent event) {
        streamBridge.send("postalCodeCreated-out-0", event);
    }

    @Override
    public void publish(PostalCodeUpdatedEvent event) {
        streamBridge.send("postalCodeUpdated-out-0", event);
    }

    @Override
    public void publish(PostalCodeDeletedEvent event) {
        streamBridge.send("postalCodeUpdated-out-0", event);
    }

}
