package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.events.ProvinceCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.ProvinceDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.ProvinceUpdatedEvent;

public interface ProvinceEventBusPort {

    void publish(ProvinceCreatedEvent event);

    void publish(ProvinceUpdatedEvent event);

    void publish(ProvinceDeletedEvent event);
}
