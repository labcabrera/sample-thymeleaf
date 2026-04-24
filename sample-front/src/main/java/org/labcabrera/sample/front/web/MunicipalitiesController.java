package org.labcabrera.sample.front.web;

import java.util.List;

import org.labcabrera.sample.front.generated.client.geo.api.MunicipalitiesApi;
import org.labcabrera.sample.front.generated.client.geo.model.CreateMunicipalityDto;
import org.labcabrera.sample.front.generated.client.geo.model.MunicipalityDto;
import org.labcabrera.sample.front.generated.client.geo.model.MunicipalityPage;
import org.labcabrera.sample.front.generated.client.geo.model.Pagination;
import org.labcabrera.sample.front.generated.client.geo.model.UpdateMunicipalityDto;
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
@RequestMapping("/municipalities")
@Slf4j
public class MunicipalitiesController {

    @Autowired
    private MunicipalitiesApi municipalitiesApi;

    @GetMapping
    public String list(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        log.trace("Fetching municipalities with query: {}", q);
        MunicipalityPage page = this.municipalitiesApi.getMunicipalitiesByRsql(q, null, null, null);
        List<MunicipalityDto> municipalities = page.getContent();
        Pagination pagination = page.getPagination();
        model.addAttribute("municipalities", municipalities);
        model.addAttribute("page", pagination.getPage());
        model.addAttribute("size", pagination.getSize());
        model.addAttribute("title", "Municipalities - Sample Front");
        return "municipalities/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("municipality", new MunicipalityDto());
        model.addAttribute("title", "Create Municipality");
        return "municipalities/form";
    }

    @PostMapping
    public String create(CreateMunicipalityDto municipality) {
        var response = municipalitiesApi.createMunicipality(municipality);
        if (response == null) {
            log.error("Error creating municipality");
            throw new RuntimeException("Error creating municipality");
        }
        return "redirect:/municipalities";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable(name = "id") String id, Model model) {
        var municipality = municipalitiesApi.getMunicipalityById(id);
        if (municipality == null) {
            log.error("Municipality not found with id: {}", id);
            throw new RuntimeException("Municipality not found");
        }
        model.addAttribute("municipality", municipality);
        model.addAttribute("title", "Edit Municipality");
        return "municipalities/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable(name = "id") String id, UpdateMunicipalityDto municipality) {
        municipalitiesApi.updateMunicipality(id, municipality);
        return "redirect:/municipalities";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") String id) {
        municipalitiesApi.deleteMunicipality(id);
        return "redirect:/municipalities";
    }

}
