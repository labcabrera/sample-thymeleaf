package org.labcabrera.sample.front.web;

import java.util.List;

import org.labcabrera.sample.front.generated.client.geo.api.CountriesApi;
import org.labcabrera.sample.front.generated.client.geo.model.CreateCountryDto;
import org.labcabrera.sample.front.generated.client.geo.model.CountryDto;
import org.labcabrera.sample.front.generated.client.geo.model.CountryPage;
import org.labcabrera.sample.front.generated.client.geo.model.Pagination;
import org.labcabrera.sample.front.generated.client.geo.model.UpdateCountryDto;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/countries")
@Slf4j
public class CountriesController {

    @Autowired
    private CountriesApi countriesApi;

    @GetMapping
    public String list(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        log.trace("Fetching countries with query: {}", q);
        CountryPage page = this.countriesApi.getCountriesByRsql(q, null, null, null);
        List<CountryDto> countries = page.getContent();
        Pagination pagination = page.getPagination();
        model.addAttribute("countries", countries);
        model.addAttribute("page", pagination.getPage());
        model.addAttribute("size", pagination.getSize());
        model.addAttribute("title", "Countries - Sample Front");
        return "countries/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("country", new CountryDto());
        model.addAttribute("title", "Create Country");
        return "countries/form";
    }

    @PostMapping
    public String create(CreateCountryDto country) {
        var response = countriesApi.createCountry(country);
        if (response == null) {
            log.error("Error creating country");
            throw new RuntimeException("Error creating country");
        }
        return "redirect:/countries";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable(name = "id") String id, Model model) {
        var country = countriesApi.getCountryById(id);
        if (country == null) {
            log.error("Country not found with id: {}", id);
            throw new RuntimeException("Country not found");
        }
        model.addAttribute("country", country);
        model.addAttribute("title", "Edit Country");
        return "countries/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable(name = "id") String id, UpdateCountryDto country) {
        countriesApi.updateCountry(id, country);
        return "redirect:/countries";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") String id) {
        countriesApi.deleteCountry(id);
        return "redirect:/countries";
    }

}
