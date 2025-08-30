package com.worch.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateChannelRequest {

    private String name;

    private String description;

    private Boolean isPrivate;

    private String password;

    private Boolean ageRestricted;

}
