package org.labcabrera.sample.front.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi.GetProvincesByRsqlQueryParams;
import org.labcabrera.sample.front.generated.client.geo.model.ProvinceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public String list(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String q,
        HttpSession session) {
        String jwt = (String) session.getAttribute("jwt");
        if (jwt == null) {
            //TODO
            throw new RuntimeException("User not authenticated");
        }
        this.log.info("Fetching provinces with query: {} ({})", q, jwt);
        
        GetProvincesByRsqlQueryParams query = new GetProvincesByRsqlQueryParams(); 
        query.q(q);

        //TODO fix pagination
        List<ProvinceDto> provinces = provincesApi.getProvincesByRsql(query);

        model.addAttribute("provinces", provinces);
        
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
    public String create(ProvinceDto province) {
        // String url = apiBaseUrl + "/provinces";
        // rest.postForEntity(url, province, Map.class);
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

    @SuppressWarnings("unchecked")
    private ProvinceDto map(Map<String, Object> m) {
        ProvinceDto p = new ProvinceDto();
        p.setId((String) m.get("id"));
        p.setCode((String) m.get("code"));
        p.setName((String) m.get("name"));
        p.setCountryCode((String) m.get("countryCode"));
        return p;
    }
}
