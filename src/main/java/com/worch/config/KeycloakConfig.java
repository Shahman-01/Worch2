package com.worch.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final OAuth2Properties oAuth2Properties;

    @Bean
    public Keycloak keycloakAdmin() {
        String clientId = oAuth2Properties.getClient()
                .getRegistration()
                .getKeycloak()
                .getClientId();

        String clientSecret = oAuth2Properties.getClient()
                .getRegistration()
                .getKeycloak()
                .getClientSecret();

        String issuerUri = oAuth2Properties.getResourceserver()
                .getJwt()
                .getIssuerUri();

        String[] issuerParts = issuerUri.split("/realms/");
        String serverUrl = issuerParts[0];
        String realm = issuerParts[1];

        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
