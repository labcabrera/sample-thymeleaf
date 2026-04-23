package org.labcabrera.sample.front.config;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    static class LenientOffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String text = p.getText();
            if (text == null || text.isEmpty()) {
                return null;
            }
            try {
                return OffsetDateTime.parse(text);
            } catch (DateTimeParseException ex) {
                // Try parsing as LocalDateTime (no offset) and convert using system default zone
                try {
                    LocalDateTime ldt = LocalDateTime.parse(text);
                    return ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime();
                } catch (DateTimeParseException ex2) {
                    // Try as Instant
                    try {
                        Instant inst = Instant.parse(text);
                        return OffsetDateTime.ofInstant(inst, ZoneId.systemDefault());
                    } catch (DateTimeParseException ex3) {
                        throw ctxt.weirdStringException(text, OffsetDateTime.class, "Unparseable date-time");
                    }
                }
            }
        }
    }

    @Bean
    public Module lenientOffsetDateTimeModule() {
        SimpleModule m = new SimpleModule();
        m.addDeserializer(OffsetDateTime.class, new LenientOffsetDateTimeDeserializer());
        return m;
    }
}
