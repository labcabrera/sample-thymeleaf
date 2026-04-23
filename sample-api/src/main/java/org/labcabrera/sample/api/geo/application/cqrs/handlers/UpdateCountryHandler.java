package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateCountryCommand;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCountryHandler implements CommandHandler<UpdateCountryCommand, Country> {

    private final CountryRepository countryRepository;
    private final Guard<Country> countryGuard;
    private final SecurityPort securityPort;

    @Override
    public Country handle(UpdateCountryCommand command) {
        var countryId = command.countryId();
        var user = securityPort.requireCurrentUser();
        log.debug("Updating country {} (user: {})", countryId, user.username());
        var existing = countryRepository.findById(countryId)
            .orElseThrow(() -> new NotFoundException("country.msg.not-found", countryId, Country.class));
        countryGuard.checkWrite(existing, user);
        var updatedData = new Country(existing.id(), command.name(), existing.createdAt(), LocalDateTime.now());
        var updated = countryRepository.update(countryId, updatedData);
        //TODO propagate event
        return updated;
    }

}
