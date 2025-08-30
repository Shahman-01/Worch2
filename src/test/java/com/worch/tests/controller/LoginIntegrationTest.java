package com.worch.tests.controller;

import static com.worch.tests.constants.TestApiUrls.INVALID_CREDENTIALS;
import static com.worch.tests.constants.TestApiUrls.LOGIN;
import static com.worch.tests.constants.TestApiUrls.REGISTER;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.worch.model.dto.request.LoginRequest;
import com.worch.model.dto.request.RegisterRequest;
import com.worch.tests.common.AbstractIntegrationTest;
import com.worch.tests.util.TestUserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("Аутентификация пользователей")
class LoginIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Успешный вход в систему")
    void login_validCredentials_success() throws Exception {
        TestUserBuilder builder = new TestUserBuilder();
        RegisterRequest registerRequest = builder.buildRegisterRequest();

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        LoginRequest loginRequest = builder.buildLoginRequest();
        mockMvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @DisplayName("Вход - неавторизован: неверные учетные данные")
    void login_invalidCredentials_unauthorized() throws Exception {
        LoginRequest invalidRequest = new LoginRequest("wronguser", "wrongpass");

        mockMvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", containsString(INVALID_CREDENTIALS)));
    }
}
