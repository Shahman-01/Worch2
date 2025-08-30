package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Информация о владельце группы")
public class GroupOwnerDto {

    @Schema(description = "Идентификатор владельца", required = true)
    private UUID id;

    @Schema(description = "Имя владельца", required = true)
    private String name;
}