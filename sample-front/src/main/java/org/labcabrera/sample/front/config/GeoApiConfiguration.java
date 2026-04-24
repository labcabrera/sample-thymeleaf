package org.labcabrera.sample.front.config;

import org.labcabrera.sample.front.generated.client.geo.ApiClient;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.CountriesApi;
import org.labcabrera.sample.front.generated.client.geo.api.MunicipalitiesApi;
import org.labcabrera.sample.front.generated.client.geo.auth.HttpBearerAuth;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GeoApiConfiguration {

    @Bean
    ApiClient usersApiClient(ObjectMapper objectMapper, @Value("${geo.api.base-path}") String basePath) {
        ApiClient apiClient = new ApiClient();
        apiClient.setObjectMapper(objectMapper);
        apiClient.setBasePath(basePath);

        // Configure a bearer auth that reads the token from the current HTTP session
        HttpBearerAuth bearer = new HttpBearerAuth("bearer");
        apiClient.addAuthorization("bearer-jwt", bearer);

        // Supplier that fetches the JWT from the current HTTP session attribute `jwt`
        Supplier<String> jwtSupplier = () -> {
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
            if (!(attrs instanceof ServletRequestAttributes)) {
                return null;
            }
            HttpServletRequest req = ((ServletRequestAttributes) attrs).getRequest();
            HttpSession session = req.getSession(false);
            if (session == null) {
                return null;
            }
            Object token = session.getAttribute("jwt");
            return token != null ? token.toString() : null;
        };

        apiClient.setBearerToken(jwtSupplier);

        // Reconfigure Feign encoder/decoder to use the provided ObjectMapper
        apiClient.setFeignBuilder(
            apiClient.getFeignBuilder()
                .encoder(new feign.form.FormEncoder(new feign.jackson.JacksonEncoder(objectMapper)))
                .decoder(new org.labcabrera.sample.front.generated.client.geo.ApiResponseDecoder(objectMapper)));
        return apiClient;
    }

    @Bean
    ProvincesApi provincesApi(ApiClient apiClient) {
        return apiClient.buildClient(ProvincesApi.class);
    }

    @Bean
    CountriesApi countriesApi(ApiClient apiClient) {
        return apiClient.buildClient(CountriesApi.class);
    }

    @Bean
    MunicipalitiesApi municipalitiesApi(ApiClient apiClient) {
        return apiClient.buildClient(MunicipalitiesApi.class);
    }

}
