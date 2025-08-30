package com.worch.mapper;

import com.worch.model.dto.request.RegisterRequest;
import com.worch.model.dto.response.UserProfileResponse;
import com.worch.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest dto);

    RegisterRequest toDto(User entity);

    UserProfileResponse toUserProfileResponse(User user);
}