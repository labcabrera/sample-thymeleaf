package org.labcabrera.sample.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register any available modules (JSR310, JDK8, etc.) from the classpath
        try {
            mapper.findAndRegisterModules();
        }
        catch (Exception ignore) {
            log.warn("Could not register Jackson modules, falling back to default ObjectMapper", ignore);
        }
        return mapper;
    }
}
