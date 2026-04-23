package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteMunicipalityCommand;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.domain.Municipality;
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
public class DeleteMunicipalityHandler implements CommandHandler<DeleteMunicipalityCommand, Void> {

    private final MunicipalityRepository municipalityRepository;
    private final Guard<Municipality> municipalityGuard;
    private final SecurityPort securityPort;

    @Override
    public Void handle(DeleteMunicipalityCommand command) {
        var user = securityPort.requireCurrentUser();
        log.debug("Deleting municipality {} (user: {})", command.municipalityId(), user.username());
        var municipality = municipalityRepository.findById(command.municipalityId())
            .orElseThrow(() -> new NotFoundException("municipality.msg.not-found", command.municipalityId(), Municipality.class));
        municipalityGuard.checkWrite(municipality, user);
        municipalityRepository.deleteById(command.municipalityId());
        return null;
    }

}
