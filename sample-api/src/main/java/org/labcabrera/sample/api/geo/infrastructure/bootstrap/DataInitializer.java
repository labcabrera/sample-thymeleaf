package org.labcabrera.sample.api.geo.infrastructure.bootstrap;

import java.time.LocalDateTime;
import java.util.UUID;

import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.domain.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ensureSchema();
        var page = Pageable.ofSize(1).withPage(0);
        try {
            var countries = countryRepository.findByRsql("", page, null);
            if (countries == null || countries.isEmpty()) {
                log.info("No countries found — seeding initial data");
                seed();
            }
            else {
                log.info("Countries present ({}), skipping seed", countries.getTotalElements());
            }
        }
        catch (Exception ex) {
            log.warn("Could not read countries on startup, attempting to seed. Reason: {}", ex.getMessage());
            seed();
        }
    }

    private void ensureSchema() {
        try {
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS countries (id VARCHAR(36) PRIMARY KEY, name VARCHAR(200), created_at TIMESTAMP, updated_at TIMESTAMP, version BIGINT)");
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS province (id VARCHAR(36) PRIMARY KEY, code VARCHAR(36), name VARCHAR(200), country_code VARCHAR(10), created_at TIMESTAMP, updated_at TIMESTAMP, version BIGINT)");
            log.info("Ensured basic schema for countries and province tables");
        }
        catch (Exception ex) {
            log.warn("Could not ensure schema: {}", ex.getMessage());
        }
    }

    private void seed() {
        LocalDateTime now = LocalDateTime.now();
        Country spain = new Country("ES", "Spain", now, null);
        Country france = new Country("FR", "France", now, null);
        Country portugal = new Country("PT", "Portugal", now, null);
        Country italy = new Country("IT", "Italy", now, null);
        countryRepository.save(france);
        countryRepository.save(spain);
        countryRepository.save(portugal);
        countryRepository.save(italy);
        // Provinces: Spain (Madrid, Barcelona)
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Madrid", "ES", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Barcelona", "ES", now, null));
        // For other countries add two most relevant provinces/regions
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Île-de-France", "FR", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Provence-Alpes-Côte d'Azur", "FR", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Lisbon", "PT", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Porto", "PT", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Lazio", "IT", now, null));
        provinceRepository.save(new Province(UUID.randomUUID().toString(), "Lombardy", "IT", now, null));
        log.info("Seeded countries and provinces");
    }

}
