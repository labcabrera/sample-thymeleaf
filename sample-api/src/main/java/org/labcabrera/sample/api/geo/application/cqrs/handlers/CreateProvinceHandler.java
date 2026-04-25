package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateProvinceCommand;
import org.labcabrera.sample.api.geo.application.ports.ProvinceEventBusPort;
import org.labcabrera.sample.api.geo.application.ports.ProvinceMetricPort;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.domain.events.ProvinceCreatedEvent;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.ConflictException;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProvinceHandler implements CommandHandler<CreateProvinceCommand, Province> {

    private final ProvinceRepository provinceRepository;
    private final Guard<Province> provinceGuard;
    private final SecurityPort securityPort;
    private final ProvinceEventBusPort eventBusPort;
    private final ProvinceMetricPort provinceMetricPort;

    @Override
    public Province handle(@Valid CreateProvinceCommand command) {
        var user = securityPort.requireCurrentUser();
        provinceGuard.checkCreate(user);
        log.debug("Creating province (user: {})", user.username());
        var current = provinceRepository.findByName(command.name());
        if (current.isPresent()) {
            throw new ConflictException("province.msg.err.already-exists");
        }
        Province province = Province.builder()
            .id(UUID.randomUUID().toString())
            .name(command.name())
            .countryId(command.countryId())
            .createdAt(LocalDateTime.now())
            .build();
        var saved = provinceRepository.save(province);
        provinceMetricPort.incrementCreatedCounter();
        eventBusPort.publish(ProvinceCreatedEvent.of(province));
        return saved;
    }

}
