package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.util.UUID;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateMunicipalityCommand;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityEventBusPort;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityMetricPort;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.domain.events.MunicipalityCreatedEvent;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.ConflictException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateMunicipalityHandler implements CommandHandler<CreateMunicipalityCommand, Municipality> {

    private final MunicipalityRepository municipalityRepository;
    private final Guard<Municipality> municipalityGuard;
    private final SecurityPort securityPort;
    private final MunicipalityEventBusPort eventBusPort;
    private final MunicipalityMetricPort municipalityMetricPort;

    @Override
    public Municipality handle(CreateMunicipalityCommand command) {
        var user = securityPort.requireCurrentUser();
        municipalityGuard.checkCreate(user);
        log.debug("Creating municipality (user: {})", user.username());
        var current = municipalityRepository.findByName(command.name());
        if (current.isPresent()) {
            throw new ConflictException("municipality.msg.err.already-exists");
        }
        Municipality municipality = new Municipality(
            UUID.randomUUID().toString(),
            command.name(),
            command.provinceId(),
            null, null);
        var saved = municipalityRepository.save(municipality);
        municipalityMetricPort.incrementCreatedCounter();
        eventBusPort.publish(new MunicipalityCreatedEvent(saved.id(), saved.name(), saved.createdAt()));
        return saved;
    }

}
