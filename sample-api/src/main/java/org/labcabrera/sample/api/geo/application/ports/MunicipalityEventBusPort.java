package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.events.MunicipalityCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityUpdatedEvent;

public interface MunicipalityEventBusPort {

    void publish(MunicipalityCreatedEvent event);

    void publish(MunicipalityUpdatedEvent event);

    void publish(MunicipalityDeletedEvent event);

}
