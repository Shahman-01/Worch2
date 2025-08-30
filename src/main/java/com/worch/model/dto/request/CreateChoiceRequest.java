package com.worch.model.dto.request;

import com.worch.model.enums.ChoiceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

public record CreateChoiceRequest(
    @NotNull UUID creatorId,
    @NotNull UUID channelId,
    @NotBlank @Size(max = 300) String title,
    String description,
    // byte[] image,
    boolean isPersonal,

    @NotNull ChoiceStatus status,
    @NotNull ZonedDateTime deadline
) {

}
