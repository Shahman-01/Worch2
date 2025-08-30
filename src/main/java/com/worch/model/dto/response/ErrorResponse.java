package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorResponse(
        @Schema(description = "Сообщение об ошибке", required = true)
        String message,

        @Schema(description = "Временная метка ошибки", required = true)
        LocalDateTime timestamp
) {
    public ErrorResponse(String message) {
        this(message, LocalDateTime.now());
    }
}