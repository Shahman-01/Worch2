package com.worch.mapper;

import com.worch.model.dto.response.StackResponseDto;
import com.worch.model.entity.Stack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StackMapper {

  StackResponseDto toDto(Stack entity);
}
