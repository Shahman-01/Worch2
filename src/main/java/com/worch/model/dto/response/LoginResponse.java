package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "Access токен", required = true)
        String accessToken,

        @Schema(description = "Refresh токен", required = true)
        String refreshToken
) {
}