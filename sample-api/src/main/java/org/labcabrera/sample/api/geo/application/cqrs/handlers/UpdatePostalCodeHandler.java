package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdatePostalCodeCommand;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatePostalCodeHandler implements CommandHandler<UpdatePostalCodeCommand, PostalCode> {

    private final PostalCodeRepository postalCodeRepository;
    private final Guard<PostalCode> postalCodeGuard;
    private final SecurityPort securityPort;

    @Override
    public PostalCode handle(UpdatePostalCodeCommand command) {
        var user = securityPort.requireCurrentUser();
        log.debug("Updating postal code (user: {})", user.username());
        String postalCodeId = command.postalCodeId();
        PostalCode existing = postalCodeRepository.findById(postalCodeId)
            .orElseThrow(() -> new NotFoundException("postalcode.msg.not-found", postalCodeId, PostalCode.class));
        postalCodeGuard.checkWrite(existing, user);
        existing.setCode(command.code());
        existing.setProvinceId(command.provinceId());
        return postalCodeRepository.save(existing);
    }

}
