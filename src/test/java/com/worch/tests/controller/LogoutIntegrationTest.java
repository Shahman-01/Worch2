package com.worch.tests.controller;

import static com.worch.tests.constants.TestApiUrls.INVALID_TOKEN;
import static com.worch.tests.constants.TestApiUrls.LOGIN;
import static com.worch.tests.constants.TestApiUrls.LOGOUT;
import static com.worch.tests.constants.TestApiUrls.LOGOUT_SUCCESS;
import static com.worch.tests.constants.TestApiUrls.REGISTER;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.worch.model.dto.request.LogoutRequest;
import com.worch.model.dto.request.RegisterRequest;
import com.worch.tests.common.AbstractIntegrationTest;
import com.worch.tests.util.TestUserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@DisplayName("Завершение сессии (Logout)")
class LogoutIntegrationTest extends AbstractIntegrationTest {

    private String registerAndLoginAndGetRefreshToken() throws Exception {
        TestUserBuilder builder = new TestUserBuilder();
        RegisterRequest registerRequest = builder.buildRegisterRequest();

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(builder.buildLoginRequest())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(responseBody);
        return json.get("refreshToken").asText();
    }

    @Test
    @DisplayName("Успешный выход из системы")
    void logout_validToken_success() throws Exception {
        String refreshToken = registerAndLoginAndGetRefreshToken();
        LogoutRequest logoutRequest = new LogoutRequest(refreshToken);

        mockMvc.perform(post(LOGOUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logoutRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LOGOUT_SUCCESS));
    }

    @Test
    @DisplayName("Выход — ошибка: пустой refresh token")
    void logout_emptyToken_badRequest() throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest("");

        mockMvc.perform(post(LOGOUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logoutRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.refreshToken").exists());
    }

    @Test
    @DisplayName("Выход — ошибка: невалидный refresh token")
    void logout_invalidToken_unauthorized() throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest("invalid-token");

        mockMvc.perform(post(LOGOUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(logoutRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", containsString(INVALID_TOKEN)));
    }

    @Test
    @DisplayName("Выход — ошибка: null refresh token")
    void logout_nullToken_badRequest() throws Exception {
        String json = "{\"refreshToken\":null}";

        mockMvc.perform(post(LOGOUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.refreshToken").exists());
    }
}
