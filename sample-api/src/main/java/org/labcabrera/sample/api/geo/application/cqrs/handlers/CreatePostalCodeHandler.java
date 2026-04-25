package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.util.UUID;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreatePostalCodeCommand;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeEventBusPort;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeMetricPort;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.geo.domain.events.PostalCodeCreatedEvent;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePostalCodeHandler implements CommandHandler<CreatePostalCodeCommand, PostalCode> {

    private final PostalCodeRepository postalCodeRepository;
    private final Guard<PostalCode> postalCodeGuard;
    private final SecurityPort securityPort;
    private final PostalCodeEventBusPort eventBusPort;
    private final PostalCodeMetricPort postalCodeMetricPort;

    @Override
    public PostalCode handle(CreatePostalCodeCommand command) {
        var user = securityPort.requireCurrentUser();
        postalCodeGuard.checkCreate(user);
        log.debug("Creating postal code (user: {})", user.username());
        var current = postalCodeRepository.findByCode(command.code());
        if (current.isPresent()) {
            throw new ConflictException("postalcode.msg.err.already-exists");
        }
        PostalCode postalCode = new PostalCode();
        postalCode.setId(UUID.randomUUID().toString());
        postalCode.setCode(command.code());
        postalCode.setMunicipalityId(command.municipalityId());
        postalCode.setProvinceId(command.provinceId());
        var saved = postalCodeRepository.save(postalCode);
        postalCodeMetricPort.incrementCreatedCounter();
        eventBusPort.publish(PostalCodeCreatedEvent.of(saved));
        return saved;
    }

}
