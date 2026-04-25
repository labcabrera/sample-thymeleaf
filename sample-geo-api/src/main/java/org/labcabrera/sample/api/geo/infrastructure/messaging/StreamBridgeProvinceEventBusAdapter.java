package org.labcabrera.sample.api.geo.infrastructure.messaging;

import org.labcabrera.sample.api.geo.application.ports.ProvinceEventBusPort;
import org.labcabrera.sample.api.geo.domain.events.ProvinceCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.ProvinceDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.ProvinceUpdatedEvent;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.infrastructure.messaging.kafka.StreamBridgeEventBusAdapter;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link CaseFolderEventBusPort} based on
 * {@link StreamBridgeEventBusAdapter}.
 */
@Service
@Slf4j
public class StreamBridgeProvinceEventBusAdapter
    extends StreamBridgeEventBusAdapter
    implements ProvinceEventBusPort {

    public StreamBridgeProvinceEventBusAdapter(StreamBridge streamBridge, SecurityPort securityPort) {
        super(streamBridge, securityPort);
    }

    @Override
    public void publish(ProvinceCreatedEvent event) {
        sendNotification("provinceCreated-out-0", event);
    }

    @Override
    public void publish(ProvinceUpdatedEvent event) {
        sendNotification("provinceUpdated-out-0", event);
    }

    @Override
    public void publish(ProvinceDeletedEvent event) {
        sendNotification("provinceDeleted-out-0", event);
    }

}
