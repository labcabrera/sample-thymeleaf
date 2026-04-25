package org.labcabrera.sample.front.web;

import org.labcabrera.sample.front.generated.client.geo.api.CountriesApi;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.MunicipalitiesApi;
import org.labcabrera.sample.front.generated.client.geo.model.CountryPage;
import org.labcabrera.sample.front.generated.client.geo.model.ProvincePage;
import org.labcabrera.sample.front.generated.client.geo.model.MunicipalityPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/geo")
@RequiredArgsConstructor
@Slf4j
public class GeoController {

    private final CountriesApi countriesApi;
    private final ProvincesApi provincesApi;
    private final MunicipalitiesApi municipalitiesApi;

    @GetMapping
    public String list(Model model) {
        log.trace("Fetching geo lists");
        CountryPage countries = null;
        ProvincePage provinces = null;
        MunicipalityPage municipalities = null;
        try {
            countries = countriesApi.getCountriesByRsql("", null, null, null);
        }
        catch (Exception ex) {
            log.warn("Could not fetch countries: {}", ex.getMessage());
        }
        try {
            provinces = provincesApi.getProvincesByRsql("", null, null, null);
        }
        catch (Exception ex) {
            log.warn("Could not fetch provinces: {}", ex.getMessage());
        }
        try {
            municipalities = municipalitiesApi.getMunicipalitiesByRsql("", null, null, null);
        }
        catch (Exception ex) {
            log.warn("Could not fetch municipalities: {}", ex.getMessage());
        }

        model.addAttribute("countries", countries != null ? countries.getContent() : java.util.List.of());
        model.addAttribute("provinces", provinces != null ? provinces.getContent() : java.util.List.of());
        model.addAttribute("municipalities", municipalities != null ? municipalities.getContent() : java.util.List.of());
        model.addAttribute("postalCodes", java.util.List.of());
        model.addAttribute("addresses", java.util.List.of());
        model.addAttribute("title", "Geo - Sample Front");
        return "geo/list";
    }

}
