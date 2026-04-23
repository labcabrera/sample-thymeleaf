package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.api.geo.domain.events.CaseFolderDeletedEvent;
import org.labcabrera.sample.api.geo.domain.events.CaseFolderUpdatedEvent;

public interface CaseFolderEventBusPort {

    void publish(CaseFolderCreatedEvent event);

    void publish(CaseFolderUpdatedEvent event);

    void publish(CaseFolderDeletedEvent event);
}
