package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.events.PostalCodeCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeUpdatedEvent;

public interface PostalCodeEventBusPort {

    void publish(PostalCodeCreatedEvent event);

    void publish(PostalCodeUpdatedEvent event);

    void publish(PostalCodeDeletedEvent event);

}
