package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteCountryCommand;
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
public class DeleteCountryHandler implements CommandHandler<DeleteCountryCommand, Void> {

    private final CountryRepository countryRepository;
    private final Guard<Country> countryGuard;
    private final SecurityPort securityPort;

    @Override
    public Void handle(DeleteCountryCommand command) {
        var user = securityPort.requireCurrentUser();
        log.debug("Deleting country {} (user: {})", command.countryId(), user.username());
        var country = countryRepository.findById(command.countryId())
            .orElseThrow(() -> new NotFoundException("country.msg.not-found", command.countryId(), Country.class));
        countryGuard.checkWrite(country, user);
        countryRepository.deleteById(command.countryId());
        return null;
    }

}
