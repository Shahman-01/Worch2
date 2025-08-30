package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "DTO ответа для сущности стэка")
public record StackResponseDto(

    @Schema(description = "Идентификатор стэка", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,

    @Schema(description = "Название стэка", example = "Java Basics")
    String title,

    @Schema(description = "Описание стэка", example = "Основы языка Java")
    String description,

    @Schema(description = "ID создателя стэка", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID creatorId,

    @Schema(description = "Флаг, указывающий, является ли стэк квизом", example = "true")
    boolean isQuiz,

    @Schema(description = "Опубликован ли стэк", example = "false")
    boolean published,

    @Schema(description = "Дата и время создания стэка", example = "2024-06-28T14:30:00Z")
    OffsetDateTime createdAt

) {
}
