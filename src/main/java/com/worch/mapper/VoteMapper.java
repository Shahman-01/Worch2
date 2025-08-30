package com.worch.mapper;

import com.worch.model.dto.response.VoteResponseDto;
import com.worch.model.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(source = "id", target = "voteId")
    VoteResponseDto toDto(Vote vote);
}

