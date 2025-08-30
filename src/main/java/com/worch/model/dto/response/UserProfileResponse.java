package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(name = "UserProfileResponse", description = "Ответ с данными профиля пользователя")
public record UserProfileResponse(
        @Schema(description = "Уникальный идентификатор пользователя", required = true)
        UUID id,

        @Schema(description = "Номер телефона")
        String phone,

        @Schema(description = "Логин пользователя", required = true)
        String login,

        @Schema(description = "Email пользователя", required = true)
        String email,

        @Schema(description = "Имя")
        String firstName,

        @Schema(description = "Фамилия")
        String lastName,

        @Schema(description = "Дата создания профиля", required = true)
        OffsetDateTime createdAt
) {
}