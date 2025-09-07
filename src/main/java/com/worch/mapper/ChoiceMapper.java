package com.worch.mapper;

import com.worch.model.dto.response.ChoicesResponseDto;
import com.worch.model.entity.Choice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {
    ChoicesResponseDto toDto(Choice choice);
}
