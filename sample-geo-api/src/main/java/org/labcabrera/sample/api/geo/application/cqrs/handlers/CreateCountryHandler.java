package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateCountryCommand;
import org.labcabrera.sample.api.geo.application.ports.CountryEventBusPort;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.domain.events.CountryCreatedEvent;
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
public class CreateCountryHandler implements CommandHandler<CreateCountryCommand, Country> {

    private final CountryRepository countryRepository;
    private final Guard<Country> countryGuard;
    private final SecurityPort securityPort;
    private final CountryEventBusPort eventBusPort;

    @Override
    public Country handle(CreateCountryCommand command) {
        var user = securityPort.requireCurrentUser();
        countryGuard.checkCreate(user);
        log.debug("Creating country (user: {})", user.username());
        var current = countryRepository.findByIdOrName(command.id(), command.name());
        if (current.isPresent()) {
            throw new ConflictException("country.msg.err.already-exists");
        }
        Country country = new Country(
            command.id().toUpperCase(),
            command.name().toUpperCase(),
            LocalDateTime.now(),
            null);
        var saved = countryRepository.save(country);
        eventBusPort.publish(new CountryCreatedEvent(saved.getId(), saved.getName(), LocalDateTime.now()));
        return saved;
    }

}
