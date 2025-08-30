package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "ExpertProfileResponse", description = "Профиль эксперта")
public record ExpertProfileResponse(

        @Schema(description = "Уникальный идентификатор профиля", required = true)
        UUID id,

        @Schema(description = "Идентификатор пользователя", required = true)
        UUID userId,

        @Schema(description = "Статус инкогнито", example = "false", required = true)
        Boolean isIncognito,

        @Schema(description = "Стоимость услуг эксперта", example = "1500", required = true)
        Integer price,

        @Schema(description = "Рейтинг эксперта", example = "4.7", required = true)
        Float rating

) {}