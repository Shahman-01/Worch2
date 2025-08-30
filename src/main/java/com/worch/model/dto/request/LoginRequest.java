package com.worch.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Поле 'login' обязательно для заполнения")
        String login,

        @NotBlank(message = "Поле 'password' обязательно для заполнения")
        String password
) {
}
