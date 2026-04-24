package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.events.CountryCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.CountryDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.CountryUpdatedEvent;

public interface CountryEventBusPort {

    void publish(CountryCreatedEvent event);

    void publish(CountryUpdatedEvent event);

    void publish(CountryDeletedEvent event);

}
