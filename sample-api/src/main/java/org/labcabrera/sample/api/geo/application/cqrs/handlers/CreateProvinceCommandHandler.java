package org.labcabrera.sample.api.geo.application.cqrs.handlers;

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
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProvinceCommandHandler implements CommandHandler<CreateProvinceCommand, Province> {

    private final ProvinceRepository provinceRepository;
    private final Guard<Province> provinceGuard;
    private final SecurityPort securityPort;
    private final ProvinceEventBusPort eventBusPort;
    private final ProvinceMetricPort provinceMetricPort;

    @Override
    public Province handle(CreateProvinceCommand command) {
        var user = securityPort.requireCurrentUser();
        provinceGuard.checkCreate(user);
        log.debug("Creating province (user: {})", user.username());
        Province province = new Province();
        province.setId(UUID.randomUUID().toString());
        province.setCode(command.code());
        province.setName(command.name());
        province.setCountryCode(command.countryCode());
        var saved = provinceRepository.save(province);
        provinceMetricPort.incrementCreatedCounter();
        eventBusPort.publish(new ProvinceCreatedEvent(saved.getId(), saved.getName(), saved.getCreatedAt()));
        return saved;
    }

}
