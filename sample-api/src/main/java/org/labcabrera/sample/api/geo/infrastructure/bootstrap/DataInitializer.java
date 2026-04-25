package org.labcabrera.sample.api.geo.infrastructure.bootstrap;

import java.time.LocalDateTime;
import java.util.UUID;

import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.domain.Province;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var page = Pageable.ofSize(1).withPage(0);
        try {
            var countries = countryRepository.findByRsql("", page, null);
            if (countries == null || countries.isEmpty()) {
                log.info("No countries found. Seeding initial data");
                seed();
            }
            else {
                log.info("Countries present ({}), skipping seed", countries.getTotalElements());
            }
        }
        catch (Exception ex) {
            log.warn("Could not read countries on startup, attempting to seed. Reason: {}", ex.getMessage());
        }
    }

    private void seed() {
        LocalDateTime now = LocalDateTime.now();
        Country[] countries = new Country[] {
            new Country("ES", "SPAIN", now, null),
            new Country("FR", "FRANCE", now, null),
            new Country("PT", "PORTUGAL", now, null),
            new Country("IT", "ITALY", now, null),
            new Country("AL", "ALBANIA", now, null),
            new Country("AD", "ANDORRA", now, null),
            new Country("AT", "AUSTRIA", now, null),
            new Country("BE", "BELGIUM", now, null),
            new Country("BG", "BULGARIA", now, null),
            new Country("HR", "CROATIA", now, null),
            new Country("CY", "CYPRUS", now, null),
            new Country("CZ", "CZECH REPUBLIC", now, null),
            new Country("DK", "DENMARK", now, null),
            new Country("EE", "ESTONIA", now, null),
            new Country("FI", "FINLAND", now, null),
            new Country("DE", "GERMANY", now, null),
            new Country("GR", "GREECE", now, null),
            new Country("HU", "HUNGARY", now, null),
            new Country("IE", "IRELAND", now, null),
            new Country("LV", "LATVIA", now, null),
            new Country("LT", "LITHUANIA", now, null),
            new Country("LU", "LUXEMBOURG", now, null),
            new Country("MT", "MALTA", now, null),
            new Country("NL", "NETHERLANDS", now, null),
            new Country("PL", "POLAND", now, null)
        };
        for (Country country : countries) {
            countryRepository.save(country);
        }
        Province[] provinces = new Province[] {
            new Province("ES-MAD", "MADRID", "ES", now, null),
            new Province("ES-BAR", "BARCELONA", "ES", now, null),
            new Province("FR-ILE-DE-FRANCE", "ÎLE-DE-FRANCE", "FR", now, null),
            new Province("FR-PROVENCE-ALTES-COTE-D-AZUR", "PROVENCE-ALTES-CÔTE D'AZUR", "FR", now, null),
            new Province("PT-LISBON", "LISBON", "PT", now, null),
            new Province("PT-PORTO", "PORTO", "PT", now, null),
            new Province("LAZIO", "LAZIO", "IT", now, null),
            new Province("LOMBARDY", "LOMBARDY", "IT", now, null)
        };
        for (Province province : provinces) {
            provinceRepository.save(province);
        }
        Municipality[] municipalities = new Municipality[] {
            new Municipality(UUID.randomUUID().toString(), "MADRID", provinces[0].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "BARCELONA", provinces[1].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "PARIS", provinces[2].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "MARSEILLE", provinces[3].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "LISBON", provinces[4].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "PORTO", provinces[5].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "ROME", provinces[6].getId(), now, null),
            new Municipality(UUID.randomUUID().toString(), "MILAN", provinces[7].getId(), now, null)
        };
        for (Municipality municipality : municipalities) {
            municipalityRepository.save(municipality);
        }
        log.info("Seeded countries, provinces and municipalities");
    }

}
