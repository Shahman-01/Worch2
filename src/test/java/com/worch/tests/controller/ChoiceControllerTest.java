package com.worch.tests.controller;

import com.worch.controllers.ChoiceController;
import com.worch.model.dto.response.ChoicesResponseDto;
import com.worch.model.enums.ChoiceStatus;
import com.worch.service.ChoicesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;


import java.time.ZonedDateTime;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Операции с чойсами")
@WebMvcTest(controllers = ChoiceController.class,
        excludeAutoConfiguration = {OAuth2ResourceServerAutoConfiguration.class,
                                    OAuth2ClientAutoConfiguration.class})
class ChoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ChoicesService choicesService;

    private ChoicesResponseDto dto1;

    @BeforeEach
    void setUp() {
        dto1 = new ChoicesResponseDto(
                null,
                null,
                null,
                "Title first",
                "Description first",
                true,
                ChoiceStatus.ACTIVE,
                ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now()

        );
    }

    @Test
    @WithMockUser
    @DisplayName("Получение всех чойсов")
    void getChoices() throws Exception {
        // arrange
        List<ChoicesResponseDto> dtos = List.of(dto1);

        when(choicesService.getAllChoices(null)).thenReturn(dtos);

        // act and assert
        mockMvc.perform(get("/api/v1/choices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title first"));

    }
}