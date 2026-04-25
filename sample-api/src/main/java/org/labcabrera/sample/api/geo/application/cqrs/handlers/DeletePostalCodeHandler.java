package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.DeletePostalCodeCommand;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeletePostalCodeHandler implements CommandHandler<DeletePostalCodeCommand, Void> {

    private final PostalCodeRepository postalCodeRepository;
    private final SecurityPort securityPort;

    @Override
    public Void handle(DeletePostalCodeCommand command) {
        var user = securityPort.requireCurrentUser();
        log.debug("Deleting postal code (user: {})", user.username());
        String id = command.postalCodeId();
        postalCodeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("postalcode.msg.not-found", id, Object.class));
        postalCodeRepository.deleteById(id);
        return null;
    }

}
