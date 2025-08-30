package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SuccessResponse(
        @Schema(description = "Сообщение о результате операции", required = true)
        String message
) {
}