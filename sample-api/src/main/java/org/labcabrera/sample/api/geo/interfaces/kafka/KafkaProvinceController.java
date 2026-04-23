package org.labcabrera.sample.api.geo.interfaces.kafka;

import java.util.function.Consumer;

import org.springframework.messaging.Message;
import org.springframework.security.core.context.SecurityContextHolder;
import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateProvinceCommand;
import org.labcabrera.sample.api.shared.application.CommandBus;
import org.labcabrera.sample.api.shared.infrastructure.messaging.kafka.AuthenticatedConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaProvinceController extends AuthenticatedConsumer {

    private final CommandBus commandBus;

    @Bean
    public Consumer<Message<CreateProvinceCommand>> onCaseFolderCreation() {
        return command -> {
            log.debug("Received province creation command: {}", command.getPayload().name());
            try {
                loadUserContext(command);
                commandBus.dispatch(command);
            }
            finally {
                SecurityContextHolder.clearContext();
            }
        };
    }

    
}
