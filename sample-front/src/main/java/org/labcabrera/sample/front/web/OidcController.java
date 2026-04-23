package org.labcabrera.sample.front.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class OidcController {

    @Value("${oidc.keycloak.base:http://localhost:8090}")
    private String keycloakBase;

    @Value("${oidc.realm:sample}")
    private String realm;

    @Value("${oidc.client-id:sample-client-front}")
    private String clientId;

    @Value("${oidc.redirect-uri:http://localhost:8081/oidc/callback}")
    private String redirectUri;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("keycloakUrl", keycloakBase);
        return "login";
    }

    @GetMapping("/oidc/login")
    public RedirectView oidcLogin() {
        String authEndpoint = String.format("%s/realms/%s/protocol/openid-connect/auth", keycloakBase, realm);
        String params = String.format("response_type=code&client_id=%s&redirect_uri=%s&scope=%s",
            urlEncode(clientId), urlEncode(redirectUri), urlEncode("openid profile email"));
        return new RedirectView(authEndpoint + "?" + params);
    }

    @GetMapping("/oidc/callback")
    public String callback(@RequestParam(required = false) String code,
                           @RequestParam(required = false) String state,
                           Model model) {
        model.addAttribute("title", "OIDC Callback");
        model.addAttribute("code", code);
        model.addAttribute("state", state);
        return "oidc/callback";
    }

    private String urlEncode(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }
}
