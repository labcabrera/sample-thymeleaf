package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteProvinceCommand;
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
public class DeleteProvinceHandler implements CommandHandler<DeleteProvinceCommand, Void> {

    private final ProvinceRepository provinceRepository;
    private final Guard<Province> provinceGuard;
    private final SecurityPort securityPort;

    @Override
    public Void handle(DeleteProvinceCommand command) {
        var user = securityPort.requireCurrentUser();
        log.debug("Deleting province {} (user: {})", command.provinceId(), user.username());
        var province = provinceRepository.findById(command.provinceId())
            .orElseThrow(() -> new NotFoundException("province.msg.not-found", command.provinceId(), Province.class));
        provinceGuard.checkWrite(province, user);
        provinceRepository.deleteById(command.provinceId());
        return null;
    }

}
