package org.labcabrera.sample.front.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.labcabrera.sample.front.web.dto.ProvinceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/provinces")
public class ProvincesController {

    private final RestTemplate rest = new RestTemplate();

    @Value("${api.base-url:http://localhost:8080/api/v1}")
    private String apiBaseUrl;

    @GetMapping
    public String list(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        String url = apiBaseUrl + "/provinces?q=" + q;
        ResponseEntity<Map> resp = rest.getForEntity(url, Map.class);
        List<Map<String, Object>> content = (List<Map<String, Object>>) resp.getBody().get("content");
        List<ProvinceDto> provinces = content.stream().map(this::map).collect(Collectors.toList());
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
        String url = apiBaseUrl + "/provinces";
        rest.postForEntity(url, province, Map.class);
        return "redirect:/provinces";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        String url = apiBaseUrl + "/provinces/" + id;
        ProvinceDto p = rest.getForObject(url, ProvinceDto.class);
        model.addAttribute("province", p);
        model.addAttribute("title", "Edit Province");
        return "provinces/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, ProvinceDto province) {
        String url = apiBaseUrl + "/provinces/" + id;
        HttpEntity<ProvinceDto> entity = new HttpEntity<>(province);
        rest.exchange(url, HttpMethod.PATCH, entity, Map.class);
        return "redirect:/provinces";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        String url = apiBaseUrl + "/provinces/" + id;
        rest.delete(url);
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
