package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateMunicipalityCommand;
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
public class UpdateMunicipalityHandler implements CommandHandler<UpdateMunicipalityCommand, Municipality> {

    private final MunicipalityRepository municipalityRepository;
    private final Guard<Municipality> municipalityGuard;
    private final SecurityPort securityPort;

    @Override
    public Municipality handle(UpdateMunicipalityCommand command) {
        var municipalityId = command.municipalityId();
        var user = securityPort.requireCurrentUser();
        log.debug("Updating municipality {} (user: {})", municipalityId, user.username());
        var existing = municipalityRepository.findById(municipalityId)
            .orElseThrow(() -> new NotFoundException("municipality.msg.not-found", municipalityId, Municipality.class));
        municipalityGuard.checkWrite(existing, user);
        String name = command.name().orElse(existing.name());
        String provinceId = command.provinceId().orElse(existing.provinceId());

        var updatedData = new Municipality(existing.id(), name, provinceId, existing.createdAt(), existing.updatedAt());

        //TODO check conflict
        var updated = municipalityRepository.update(municipalityId, updatedData);
        return updated;
    }

}
