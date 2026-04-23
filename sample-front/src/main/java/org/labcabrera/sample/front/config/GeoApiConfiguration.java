package org.labcabrera.sample.front.config;

import org.labcabrera.sample.front.generated.client.geo.ApiClient;
import org.labcabrera.sample.front.generated.client.geo.api.ProvincesApi;
import org.labcabrera.sample.front.generated.client.geo.auth.HttpBearerAuth;
import org.labcabrera.sample.front.generated.client.geo.auth.OauthClientCredentialsGrant;
import org.labcabrera.sample.front.generated.client.geo.auth.OauthPasswordGrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GeoApiConfiguration {

    // @Autowired
    // private ObjectMapper objectMapper;

    @Bean
    ApiClient usersApiClient(ObjectMapper objectMapper, @Value("${geo.api.base-path}") String basePath) {
        ApiClient apiClient = new ApiClient();
        apiClient.setObjectMapper(objectMapper);
        apiClient.setBasePath(basePath);

        // String authType = properties.getAuthType();
        // if (authType != null) {
        //     switch (authType) {
        //     case "bearer": {
        //         String token = properties.getBearerToken();
        //         HttpBearerAuth bearer = new HttpBearerAuth("bearer");
        //         if (token != null) {
        //             bearer.setBearerToken(token);
        //         }
        //         apiClient.addAuthorization(properties.getAuthName(), bearer);
        //         break;
        //     }
        //     case "oauth-password": {
        //         UsersClientProperties.Oauth o = properties.getOauth();
        //         OauthPasswordGrant oauth = new OauthPasswordGrant(o.getTokenUrl(), o.getScopes());
        //         oauth.configure(o.getUsername(), o.getPassword(), o.getClientId(), o.getClientSecret());
        //         apiClient.addAuthorization(properties.getAuthName(), oauth);
        //         apiClient.registerAccessTokenListener(
        //             token -> log.info("Users client obtained access token (expiresIn={}s)", token.getExpiresIn()));
        //         break;
        //     }
        //     case "oauth-client-credentials": {
        //         UsersClientProperties.Oauth o = properties.getOauth();
        //         OauthClientCredentialsGrant oauth = new OauthClientCredentialsGrant(o.getAuthorizationUrl(), o.getTokenUrl(),
        //             o.getScopes());
        //         oauth.configure(o.getClientId(), o.getClientSecret());
        //         apiClient.addAuthorization(properties.getAuthName(), oauth);
        //         apiClient.registerAccessTokenListener(
        //             token -> log.info("Users client obtained access token (expiresIn={}s)", token.getExpiresIn()));
        //         break;
        //     }
        //     default:
        //         // none
        //     }
        // }

        return apiClient;
    }

    @Bean
    ProvincesApi provincesApi(ApiClient apiClient) {
        return apiClient.buildClient(ProvincesApi.class);
    }

}
