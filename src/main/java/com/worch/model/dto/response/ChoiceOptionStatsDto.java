package com.worch.model.dto.response;

import java.util.UUID;

public record ChoiceOptionStatsDto(
        UUID id,
        String text,
        int position,
        long votes,
        boolean isMyOption
) {}

