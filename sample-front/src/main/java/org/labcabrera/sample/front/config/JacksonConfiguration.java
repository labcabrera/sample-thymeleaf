package org.labcabrera.sample.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register any available modules (JSR310, JDK8, etc.) from the classpath
        try {
            mapper.findAndRegisterModules();
        }
        catch (Exception ex) {
            // ignore: safe fallback to a plain ObjectMapper
        }
        return mapper;
    }
}
