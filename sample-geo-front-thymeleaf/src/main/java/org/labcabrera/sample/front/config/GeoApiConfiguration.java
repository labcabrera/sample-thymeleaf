package org.labcabrera.sample.front.config;

import org.labcabrera.sample.front.generated.client.geo.ApiClient;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.api.CountriesApi;
import org.labcabrera.sample.front.generated.client.geo.api.MunicipalitiesApi;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoApiConfiguration {

    @Bean
    ApiClient usersApiClient(@Value("${geo.api.base-path}") String basePath) {
        JacksonJsonHttpMessageConverter converter = new JacksonJsonHttpMessageConverter();
        RestClient restClient = RestClient.builder()
            .configureMessageConverters(b -> b.withJsonConverter(converter))
            .build();
        ApiClient apiClient = new ApiClient(restClient);
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
        apiClient.setBasePath(basePath);
        apiClient.setAccessToken(jwtSupplier);
        return apiClient;
    }

    @Bean
    ProvincesApi provincesApi(ApiClient apiClient) {
        return new ProvincesApi(apiClient);
    }

    @Bean
    CountriesApi countriesApi(ApiClient apiClient) {
        return new CountriesApi(apiClient);
    }

    @Bean
    MunicipalitiesApi municipalitiesApi(ApiClient apiClient) {
        return new MunicipalitiesApi(apiClient);
    }

}
