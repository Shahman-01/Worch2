package com.worch.model.dto.response;

import com.worch.model.enums.ChoiceStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ChoicesResponseDto(
        UUID id,
        UUID creatorId,
        UUID channelId,
        String title,
        String description,
        boolean personal,
        ChoiceStatus status,
        ZonedDateTime deadline,
        ZonedDateTime createdAt
) {
}
