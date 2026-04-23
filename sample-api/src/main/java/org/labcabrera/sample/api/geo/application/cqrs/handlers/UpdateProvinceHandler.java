package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import java.time.LocalDateTime;

import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateProvinceCommand;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
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
public class UpdateProvinceHandler implements CommandHandler<UpdateProvinceCommand, Province> {

    private final ProvinceRepository provinceRepository;
    private final Guard<Province> provinceGuard;
    private final SecurityPort securityPort;

    @Override
    public Province handle(UpdateProvinceCommand command) {
        var provinceId = command.provinceId();
        var user = securityPort.requireCurrentUser();
        log.debug("Updating province {} (user: {})", provinceId, user.username());
        var existing = provinceRepository.findById(provinceId)
            .orElseThrow(() -> new NotFoundException("province.msg.not-found", provinceId, Province.class));
        provinceGuard.checkWrite(existing, user);

        String code = command.code().orElse(existing.code());
        String name = command.name().orElse(existing.name());
        String countryCode = command.countryCode().orElse(existing.countryCode());

        var updatedData = new Province(existing.id(), code, name, countryCode, existing.createdAt(), LocalDateTime.now());

        //TODO check conflict
        var updated = provinceRepository.update(provinceId, updatedData);
        return updated;
    }

}
