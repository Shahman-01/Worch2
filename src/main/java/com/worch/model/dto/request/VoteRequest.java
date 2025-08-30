package com.worch.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteRequest(
        @NotNull(message = "Choice ID is required")
        UUID choice,

        @NotNull(message = "Option ID is required")
        UUID option
) {
}
