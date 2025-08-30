package com.worch.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.net.URISyntaxException;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2")
public class OAuth2Properties {

    @NotNull
    @Valid
    private ResourceServer resourceserver;

    @NotNull
    @Valid
    private Client client;

    public String getAuthServerUrl() {
        try {
            URI uri = new URI(resourceserver.getJwt().getIssuerUri());
            return uri.getScheme() + "://" + uri.getHost() + (uri.getPort() > 0 ? ":" + uri.getPort() : "");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid issuer URI", e);
        }
    }

    public String getRealm() {
        try {
            URI uri = new URI(resourceserver.getJwt().getIssuerUri());
            String path = uri.getPath();
            String[] parts = path.split("/");
            if (parts.length < 3 || !parts[1].equals("realms")) {
                throw new IllegalArgumentException("Invalid realm format in issuer URI: " + path);
            }
            return parts[2];
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid issuer URI", e);
        }
    }

    public String getClientId() {
        return client.getRegistration().getKeycloak().getClientId();
    }

    public String getClientSecret() {
        return client.getRegistration().getKeycloak().getClientSecret();
    }

    @Data
    public static class ResourceServer {
        @NotNull
        @Valid
        private Jwt jwt;

        @Data
        public static class Jwt {
            @NotBlank
            private String issuerUri;
        }
    }

    @Data
    public static class Client {
        @Valid
        private Provider provider;
        @NotNull
        @Valid
        private Registration registration;

        @Data
        public static class Provider {
            @Valid
            private KeycloakProvider keycloak;

            @Data
            public static class KeycloakProvider {
                private String issuerUri;
                private String userNameAttribute;
            }
        }

        @Data
        public static class Registration {
            @NotNull
            @Valid
            private KeycloakRegistration keycloak;

            @Data
            public static class KeycloakRegistration {
                @NotBlank
                private String clientId;
                @NotBlank
                private String clientSecret;
                private String scope;
            }
        }
    }
}
