package com.worch.tests.controller;

import static com.worch.tests.constants.TestApiUrls.INVALID_EMAIL;
import static com.worch.tests.constants.TestApiUrls.LOGIN_EXISTS;
import static com.worch.tests.constants.TestApiUrls.REGISTER;
import static com.worch.tests.constants.TestApiUrls.REGISTRATION_SUCCESS;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.worch.model.dto.request.RegisterRequest;
import com.worch.tests.common.AbstractIntegrationTest;
import com.worch.tests.util.TestUserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("Регистрация пользователей")
class RegistrationIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void register_validData_success() throws Exception {
        RegisterRequest request = new TestUserBuilder().buildRegisterRequest();

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(REGISTRATION_SUCCESS));
    }

    @Test
    @DisplayName("Регистрация - конфликт: логин уже существует")
    void register_duplicateLogin_conflict() throws Exception {
        String login = "duplicate_user_" + System.currentTimeMillis();
        RegisterRequest request1 = new TestUserBuilder().withLogin(login).buildRegisterRequest();
        RegisterRequest request2 = new TestUserBuilder().withLogin(login).buildRegisterRequest();

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", containsString(LOGIN_EXISTS)));
    }

    @Test
    @DisplayName("Регистрация - валидационная ошибка: некорректный email")
    void register_invalidEmail_validationError() throws Exception {
        RegisterRequest invalidRequest = new TestUserBuilder().withEmail("invalid-email").buildRegisterRequest();

        mockMvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.email[0]", containsString(INVALID_EMAIL)));
    }
}
