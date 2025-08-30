package com.worch.tests.controller;

import com.worch.model.dto.request.LoginRequest;
import com.worch.model.dto.request.RegisterRequest;
import com.worch.repository.UserRepository;
import com.worch.tests.common.AbstractIntegrationTest;
import com.worch.tests.util.TestUserBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerTest extends AbstractIntegrationTest{

    @Autowired
    UserRepository userRepository;

    private String jwt;
    private UUID userId;

    // Создаём пользователя, получаем userId и JWT
    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();
        TestUserBuilder builder = new TestUserBuilder();
        RegisterRequest registerRequest = builder.buildRegisterRequest();

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        userId = userRepository.findAll().get(0).getId();

        LoginRequest loginRequest = builder.buildLoginRequest();

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        jwt = objectMapper.readTree(response).get("accessToken").asText();
    }

    @Test
    @DisplayName("Доступ к профилю с валидным JWT")
    void getProfile_shouldReturnProfile() throws Exception {
        mockMvc.perform(get("/api/v1/profile?id={id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+79991234567"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("Иван"))
                .andExpect(jsonPath("$.lastName").value("Иванов"));
    }

    @Test
    @DisplayName("Доступ к профилю без JWT")
    void getProfile_withoutJwt_shouldFail() throws Exception {
        mockMvc.perform(get("/api/v1/profile?id={id}", userId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Доступ к профилю с невалидным JWT")
    void getProfile_withInvalidJwt_shouldFail() throws Exception {
        mockMvc.perform(get("/api/v1/profile?id={id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid_token"))
                .andExpect(status().isUnauthorized());
    }

}
