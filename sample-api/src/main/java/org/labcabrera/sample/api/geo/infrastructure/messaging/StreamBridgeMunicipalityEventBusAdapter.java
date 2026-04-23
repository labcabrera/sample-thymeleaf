package org.labcabrera.sample.api.geo.infrastructure.messaging;

import org.labcabrera.sample.api.geo.application.ports.MunicipalityEventBusPort;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityUpdatedEvent;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.infrastructure.messaging.kafka.StreamBridgeEventBusAdapter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class StreamBridgeMunicipalityEventBusAdapter extends StreamBridgeEventBusAdapter
    implements MunicipalityEventBusPort {

    public StreamBridgeMunicipalityEventBusAdapter(StreamBridge streamBridge, SecurityPort securityPort) {
        super(streamBridge, securityPort);
    }

    @Override
    public void publish(MunicipalityCreatedEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void publish(MunicipalityUpdatedEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void publish(MunicipalityDeletedEvent event) {
        // TODO Auto-generated method stub
    }

}
