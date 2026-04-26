package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateProvinceCommand;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.application.ports.ProvinceEventBusPort;
import org.labcabrera.sample.api.geo.application.ports.ProvinceMetricPort;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.domain.events.ProvinceCreatedEvent;
import org.labcabrera.sample.api.shared.application.CommandHandler;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.api.shared.domain.exceptions.ConflictException;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProvinceHandler implements CommandHandler<CreateProvinceCommand, Province> {

    private final ProvinceRepository provinceRepository;
    private final CountryRepository countryRepository;
    private final Guard<Province> provinceGuard;
    private final SecurityPort securityPort;
    private final ProvinceEventBusPort eventBusPort;
    private final ProvinceMetricPort provinceMetricPort;

    @Override
    public Province handle(@Valid CreateProvinceCommand command) {
        var user = securityPort.requireCurrentUser();
        provinceGuard.checkCreate(user);
        log.debug("Creating province (user: {})", user.username());
        provinceRepository.findByName(command.name()).ifPresent(p -> {
            throw new ConflictException("province.msg.err.already-exists");
        });
        countryRepository.findById(command.countryId())
            .orElseThrow(() -> new BadRequestException("province.msg.err.country-not-found", command.countryId()));
        Province province = Province.builder()
            .id(command.id().toUpperCase())
            .name(command.name().toUpperCase())
            .countryId(command.countryId())
            .createdAt(LocalDateTime.now())
            .build();
        var saved = provinceRepository.save(province);
        provinceMetricPort.incrementCreatedCounter();
        eventBusPort.publish(ProvinceCreatedEvent.of(province));
        return saved;
    }

}
