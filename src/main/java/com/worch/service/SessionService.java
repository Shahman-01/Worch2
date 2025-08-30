package com.worch.service;

import com.worch.config.OAuth2Properties;
import com.worch.exceptions.UserSessionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final RestTemplate restTemplate;
    private final OAuth2Properties oauth2Properties;

    public void logout(String refreshToken) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    buildLogoutUrl(),
                    new HttpEntity<>(createLogoutParams(refreshToken), createHeaders()),
                    String.class
            );
            validateLogoutResponse(response);
            log.debug("Пользователь успешно вышел из системы");
        } catch (RestClientException e) {
            String errorMessage = "Ошибка при завершении сессии: " + e.getMessage();
            log.error(errorMessage);
            throw new UserSessionException(errorMessage, e);
        }
    }

    private String buildLogoutUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(oauth2Properties.getAuthServerUrl())
                .path("/realms/{realm}/protocol/openid-connect/logout")
                .buildAndExpand(oauth2Properties.getRealm())
                .toUriString();
    }

    private MultiValueMap<String, String> createLogoutParams(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", oauth2Properties.getClientId());
        params.add("client_secret", oauth2Properties.getClientSecret());
        params.add("refresh_token", refreshToken);
        return params;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private void validateLogoutResponse(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            String error = "Keycloak вернул ошибку: " + response.getBody();
            log.error(error);
            throw new UserSessionException(error);
        }
    }
}
