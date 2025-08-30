package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Ответ с информацией о голосе")
public record VoteResponseDto(
        @Schema(description = "Уникальный идентификатор голоса", required = true)
        UUID voteId,

        @Schema(description = "Уникальный идентификатор выбора", required = true)
        UUID choiceId,

        @Schema(description = "Уникальный идентификатор опции", required = true)
        UUID optionId,

        @Schema(description = "Уникальный идентификатор пользователя", required = true)
        UUID userId,

        @Schema(description = "Дата и время когда был сделан голос", required = true)
        OffsetDateTime votedAt
) {
}
