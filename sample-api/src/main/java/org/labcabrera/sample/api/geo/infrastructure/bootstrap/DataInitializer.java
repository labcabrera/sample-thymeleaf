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
        log.info("Seeded {} countries", countries.length);
        Province[] provinces = new Province[] {
            new Province("ES-ACO", "A CORUÑA", "ES", now, null),
            new Province("ES-ALA", "ÁLAVA (ARABA)", "ES", now, null),
            new Province("ES-ALI", "ALICANTE (ALACANT)", "ES", now, null),
            new Province("ES-ALM", "ALMERÍA", "ES", now, null),
            new Province("ES-AST", "ASTURIAS", "ES", now, null),
            new Province("ES-AVI", "ÁVILA", "ES", now, null),
            new Province("ES-BAD", "BADAJOZ", "ES", now, null),
            new Province("ES-BAR", "BARCELONA", "ES", now, null),
            new Province("ES-BIZ", "BIZKAIA (VIZCAYA)", "ES", now, null),
            new Province("ES-BUR", "BURGOS", "ES", now, null),
            new Province("ES-CAC", "CÁCERES", "ES", now, null),
            new Province("ES-CAD", "CÁDIZ", "ES", now, null),
            new Province("ES-CAN", "CANTABRIA", "ES", now, null),
            new Province("ES-CAS", "CASTELLÓN (CASTELLÓ)", "ES", now, null),
            new Province("ES-CRE", "CIUDAD REAL", "ES", now, null),
            new Province("ES-COR", "CÓRDOBA", "ES", now, null),
            new Province("ES-CUE", "CUENCA", "ES", now, null),
            new Province("ES-GIR", "GIRONA", "ES", now, null),
            new Province("ES-GIP", "GIPUZKOA (GUIPÚZCOA)", "ES", now, null),
            new Province("ES-GRA", "GRANADA", "ES", now, null),
            new Province("ES-GUA", "GUADALAJARA", "ES", now, null),
            new Province("ES-HUE", "HUELVA", "ES", now, null),
            new Province("ES-HUS", "HUESCA", "ES", now, null),
            new Province("ES-BAL", "ILLES BALEARS (ISLAS BALEARES)", "ES", now, null),
            new Province("ES-JAE", "JAÉN", "ES", now, null),
            new Province("ES-RIO", "LA RIOJA", "ES", now, null),
            new Province("ES-LPA", "LAS PALMAS", "ES", now, null),
            new Province("ES-LEO", "LEÓN", "ES", now, null),
            new Province("ES-LLE", "LLEIDA", "ES", now, null),
            new Province("ES-LUG", "LUGO", "ES", now, null),
            new Province("ES-MAD", "MADRID", "ES", now, null),
            new Province("ES-MAL", "MÁLAGA", "ES", now, null),
            new Province("ES-MUR", "MURCIA", "ES", now, null),
            new Province("ES-NAV", "NAVARRA", "ES", now, null),
            new Province("ES-OUR", "OURENSE", "ES", now, null),
            new Province("ES-PAL", "PALENCIA", "ES", now, null),
            new Province("ES-PON", "PONTEVEDRA", "ES", now, null),
            new Province("ES-SAL", "SALAMANCA", "ES", now, null),
            new Province("ES-TEN", "SANTA CRUZ DE TENERIFE", "ES", now, null),
            new Province("ES-SEG", "SEGOVIA", "ES", now, null),
            new Province("ES-SEV", "SEVILLA", "ES", now, null),
            new Province("ES-SOR", "SORIA", "ES", now, null),
            new Province("ES-TAR", "TARRAGONA", "ES", now, null),
            new Province("ES-TER", "TERUEL", "ES", now, null),
            new Province("ES-TOL", "TOLEDO", "ES", now, null),
            new Province("ES-VAL", "VALENCIA (VALÈNCIA)", "ES", now, null),
            new Province("ES-VDL", "VALLADOLID", "ES", now, null),
            new Province("ES-ZAM", "ZAMORA", "ES", now, null),
            new Province("ES-ZAR", "ZARAGOZA", "ES", now, null),
            new Province("FR-PRO", "PROVENCE-ALTES-CÔTE D'AZUR", "FR", now, null),
            new Province("PT-LIB", "LISBON", "PT", now, null),
            new Province("PT-POR", "PORTO", "PT", now, null),
            new Province("IT-LAZ", "LAZIO", "IT", now, null),
            new Province("IT-LOM", "LOMBARDY", "IT", now, null)
        };
        for (Province province : provinces) {
            try {
                provinceRepository.save(province);
            }
            catch (Exception ex) {
                log.warn("Could not save province {}: {}", province.getId(), ex.getMessage());
            }
        }
        log.info("Seeded {} provinces", provinces.length);
        Municipality[] municipalities = new Municipality[] {
            new Municipality(UUID.randomUUID().toString(), "MADRID", "ES-MAD", now, null),
            new Municipality(UUID.randomUUID().toString(), "BARCELONA", "ES-BAR", now, null),
            new Municipality(UUID.randomUUID().toString(), "PARIS", "FR-PRO", now, null),
            new Municipality(UUID.randomUUID().toString(), "MARSEILLE", "FR-PRO", now, null),
            new Municipality(UUID.randomUUID().toString(), "LISBON", "PT-LIB", now, null),
            new Municipality(UUID.randomUUID().toString(), "PORTO", "PT-POR", now, null),
            new Municipality(UUID.randomUUID().toString(), "ROME", "IT-LAZ", now, null),
            new Municipality(UUID.randomUUID().toString(), "MILAN", "IT-LOM", now, null)
        };
        for (Municipality municipality : municipalities) {
            try {
                municipalityRepository.save(municipality);
            }
            catch (Exception ex) {
                log.warn("Could not save municipality {}: {}", municipality, ex.getMessage());
            }
        }
        log.info("Seeded {} municipalities", municipalities.length);
        log.info("Seeded countries, provinces and municipalities");
    }

}
