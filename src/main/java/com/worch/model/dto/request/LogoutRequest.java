package com.worch.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LogoutRequest(
        @NotNull(message = "Refresh token обязателен")
        @NotBlank(message = "Refresh token не может быть пустым")
        String refreshToken
) {
}
