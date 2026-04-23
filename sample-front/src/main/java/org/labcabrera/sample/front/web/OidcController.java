package org.labcabrera.sample.front.web;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.HttpSession;

import com.nimbusds.jose.crypto.RSASSAVerifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class OidcController {

    @Value("${oidc.keycloak.base:http://localhost:8090}")
    private String keycloakBase;

    @Value("${oidc.realm:sample}")
    private String realm;

    @Value("${oidc.client-id:sample-client}")
    private String clientId;

    // @Value("${oidc.client-id:sample-client}")
    // private String clientAuthId;

    @Value("${oidc.client-secret:LddOn5YJ5nL5w7awnt4kJMbmz27t5Rf3}")
    private String clientSecret;

    @Value("${oidc.redirect-uri:http://localhost:8081/oidc/callback}")
    private String redirectUri;

    private final RestTemplate rest = new RestTemplate();

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("keycloakUrl", keycloakBase);
        return "login";
    }

    @GetMapping("/oidc/login")
    public RedirectView oidcLogin(HttpSession session) {
        String authEndpoint = String.format("%s/realms/%s/protocol/openid-connect/auth", keycloakBase, realm);

        // generate PKCE code_verifier and code_challenge
        String codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);

        // store verifier in session for later token exchange
        session.setAttribute("pkce_code_verifier", codeVerifier);

        String params = String.format(
            "response_type=code&client_id=%s&redirect_uri=%s&scope=%s&code_challenge=%s&code_challenge_method=S256",
            urlEncode(clientId), urlEncode(redirectUri), urlEncode("openid profile email"), urlEncode(codeChallenge));
        return new RedirectView(authEndpoint + "?" + params);
    }

    @GetMapping("/oidc/callback")
    public String callback(@RequestParam(required = false) String code,
                           @RequestParam(required = false) String state,
                           Model model,
                           HttpSession session) throws Exception {
        model.addAttribute("title", "OIDC Callback");
        model.addAttribute("code", code);
        model.addAttribute("state", state);

        if (code != null && !code.isEmpty()) {
            // Exchange code for tokens
            String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakBase, realm);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String body = String.format("grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s",
                urlEncode(code), urlEncode(redirectUri), urlEncode(clientId));
            if (clientSecret != null && !clientSecret.isBlank()) {
                body += "&client_secret=" + urlEncode(clientSecret);
            }

            // include PKCE code_verifier if present
            String codeVerifier = (String) session.getAttribute("pkce_code_verifier");
            if (codeVerifier != null && !codeVerifier.isBlank()) {
                body += "&code_verifier=" + urlEncode(codeVerifier);
            }

            var request = new HttpEntity<>(body, headers);
            var tokenResponse = rest.postForEntity(tokenEndpoint, request, Map.class);
            var tokens = tokenResponse.getBody();

            String idToken = (String) tokens.get("id_token");
            String accessToken = (String) tokens.get("access_token");

            boolean valid = false;
            if (idToken != null) {
                valid = validateIdToken(idToken);
            }
            if (valid) {
                // store JWT in session
                session.setAttribute("jwt", idToken != null ? idToken : accessToken);
                model.addAttribute("message", "Token validated and stored in session.");
            } else {
                model.addAttribute("message", "Token validation failed.");
            }
        }

        return "oidc/callback";
    }

    private boolean validateIdToken(String idToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            String kid = signedJWT.getHeader().getKeyID();
            String jwksUrl = String.format("%s/realms/%s/protocol/openid-connect/certs", keycloakBase, realm);
            String jwksJson = rest.getForObject(jwksUrl, String.class);
            JWKSet jwkSet = JWKSet.parse(jwksJson);
            JWK jwk = jwkSet.getKeyByKeyId(kid);
            if (jwk == null) {
                return false;
            }
            if (!(jwk instanceof RSAKey)) {
                return false;
            }
            RSAKey rsaKey = (RSAKey) jwk;
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
            boolean signatureValid = signedJWT.verify(verifier);
            if (!signatureValid) {
                return false;
            }
            // Basic claims validation: expiration, issuer, audience
            var claims = signedJWT.getJWTClaimsSet();
            Instant now = Instant.now();
            if (claims.getExpirationTime() == null || claims.getExpirationTime().toInstant().isBefore(now)) {
                return false;
            }
            String issuer = claims.getIssuer();
            String expectedIssuer = String.format("%s/realms/%s", keycloakBase, realm);
            if (!expectedIssuer.equals(issuer)) {
                return false;
            }
            if (!claims.getAudience().contains(clientId)) {
                return false;
            }
            return true;
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    private String urlEncode(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }

    private String generateCodeVerifier() {
        byte[] random = new byte[64];
        new SecureRandom().nextBytes(random);
        String s = Base64.getUrlEncoder().withoutPadding().encodeToString(random);
        // ensure length between 43 and 128
        if (s.length() < 43) {
            s = s + "A".repeat(43 - s.length());
        } else if (s.length() > 128) {
            s = s.substring(0, 128);
        }
        return s;
    }

    private String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
