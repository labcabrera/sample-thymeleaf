package org.labcabrera.sample.front.web;

import java.util.List;
import java.util.Map;

import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi.GetByRsqlQueryParams;
import org.labcabrera.sample.front.generated.client.geo.model.ApiResponse;
import org.labcabrera.sample.front.generated.client.geo.model.CreateProvinceDto;
import org.labcabrera.sample.front.generated.client.geo.model.Pagination;
import org.labcabrera.sample.front.generated.client.geo.model.Province;
import org.labcabrera.sample.front.generated.client.geo.model.ProvinceDto;
import org.labcabrera.sample.front.generated.client.geo.model.ProvincePage;
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

    @GetMapping
    public String list(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        log.trace("Fetching provinces with query: {}", q);
        GetByRsqlQueryParams query = new GetByRsqlQueryParams();
        query.q(q);
        ApiResponse<ProvincePage> response = provincesApi.getByRsqlWithHttpInfo(query);
        if(response.getStatusCode() != 200) {
            log.error("Error fetching provinces: {}", response.getStatusCode());
            throw new RuntimeException("Error fetching provinces");
        }
        ProvincePage page = response.getData();
        List<ProvinceDto> provinces = page.getContent();
        Pagination pagination = page.getPagination();
        model.addAttribute("provinces", provinces);
        model.addAttribute("page", pagination.getPage());
        model.addAttribute("size", pagination.getSize());
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
        Province response = provincesApi.createProvince(province);
        if(response == null) {
            log.error("Error creating province");
            throw new RuntimeException("Error creating province");
        }
        return "redirect:/provinces";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        // String url = apiBaseUrl + "/provinces/" + id;
        // ProvinceDto p = rest.getForObject(url, ProvinceDto.class);
        // model.addAttribute("province", p);
        // model.addAttribute("title", "Edit Province");
        return "provinces/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, ProvinceDto province) {
        // String url = apiBaseUrl + "/provinces/" + id;
        // HttpEntity<ProvinceDto> entity = new HttpEntity<>(province);
        // rest.exchange(url, HttpMethod.PATCH, entity, Map.class);
        return "redirect:/provinces";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        // String url = apiBaseUrl + "/provinces/" + id;
        // rest.delete(url);
        return "redirect:/provinces";
    }

    private ProvinceDto map(Map<String, Object> m) {
        ProvinceDto p = new ProvinceDto();
        p.setId((String) m.get("id"));
        p.setCode((String) m.get("code"));
        p.setName((String) m.get("name"));
        p.setCountryCode((String) m.get("countryCode"));
        return p;
    }
}
