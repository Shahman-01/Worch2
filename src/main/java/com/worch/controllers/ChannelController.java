package com.worch.controllers;


import com.worch.mapper.ChannelMapper;
import com.worch.model.dto.response.ChannelResponseDto;
import com.worch.model.entity.Channel;
import com.worch.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final ChannelMapper channelMapper;


    @GetMapping("/{id}")
    public ResponseEntity<ChannelResponseDto> getChannelById(@PathVariable @Valid String id) {
        UUID uuid;
        uuid = UUID.fromString(id);
        Channel channel = channelService.findChannel(uuid);
        return ResponseEntity.ok(channelMapper.toDto(channel));
    }
}
