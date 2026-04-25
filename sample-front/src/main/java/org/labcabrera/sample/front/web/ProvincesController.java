package org.labcabrera.sample.front.web;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.CountriesApi;
import org.labcabrera.sample.front.generated.client.geo.model.CreateProvinceDto;
import org.labcabrera.sample.front.generated.client.geo.model.Pagination;
import org.labcabrera.sample.front.generated.client.geo.model.ProvinceDto;
import org.labcabrera.sample.front.generated.client.geo.model.ProvincePage;
import org.labcabrera.sample.front.generated.client.geo.model.UpdateProvinceDto;
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
@RequestMapping("/provinces")
@Slf4j
public class ProvincesController {

    @Autowired
    private ProvincesApi provincesApi;

    @Autowired
    private CountriesApi countriesApi;

    @GetMapping
    public String list(Model model,
        @RequestParam(value = "q", required = false, defaultValue = "") String q,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageParam,
        @RequestParam(value = "size", required = false, defaultValue = "10") Integer sizeParam) {
        log.trace("Fetching provinces with query: {} (page={}, size={})", q, pageParam, sizeParam);
        ProvincePage page = this.provincesApi.getProvincesByRsql(q, pageParam, sizeParam, Arrays.asList("name", "asc"));
        List<ProvinceDto> provinces = page.getContent();
        // load countries for the filter select
        List<org.labcabrera.sample.front.generated.client.geo.model.CountryDto> countries = java.util.List.of();
        try {
            var cp = countriesApi.getCountriesByRsql("", 0, 1000, Arrays.asList("name", "asc"));
            countries = cp != null ? cp.getContent() : java.util.List.of();
        }
        catch (Exception ex) {
            log.warn("Could not fetch countries for provinces filter: {}", ex.getMessage());
        }
        Pagination pagination = page.getPagination();
        model.addAttribute("provinces", provinces);
        model.addAttribute("countries", countries);
        model.addAttribute("page", pagination.getPage());
        model.addAttribute("size", pagination.getSize());
        model.addAttribute("totalPages", pagination.getTotalPages());
        model.addAttribute("q", q);
        model.addAttribute("title", "Provinces - Sample Front");
        return "provinces/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("province", new ProvinceDto());
        model.addAttribute("title", "Create Province");
        return "provinces/form";
    }

    @PostMapping
    public String create(CreateProvinceDto province) {
        var response = provincesApi.createProvince(province);
        if (response == null) {
            log.error("Error creating province");
            throw new RuntimeException("Error creating province");
        }
        return "redirect:/provinces";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        var province = provincesApi.getProvinceById(id);
        if (province == null) {
            log.error("Province not found with id: {}", id);
            throw new RuntimeException("Province not found");
        }
        model.addAttribute("province", province);
        model.addAttribute("title", "Edit Province");
        return "provinces/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, UpdateProvinceDto province) {
        provincesApi.updateProvince(id, province);
        return "redirect:/provinces";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        provincesApi.deleteProvince(id);
        return "redirect:/provinces";
    }
}
