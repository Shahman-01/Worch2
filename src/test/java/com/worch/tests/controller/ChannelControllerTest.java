package com.worch.tests.controller;

import com.worch.model.dto.response.ChannelResponseDto;
import com.worch.model.entity.Channel;
import com.worch.repository.ChannelRepository;
import com.worch.tests.common.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static com.worch.tests.constants.TestApiUrls.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Операции с каналами")
class ChannelControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ChannelRepository channelRepository;

    private Channel testChannel;

    @BeforeEach
    void setUp() {
        channelRepository.deleteAll();

        testChannel = new Channel();
        testChannel.setName("Test Channel");
        testChannel.setDescription("Test Description");
        testChannel = channelRepository.save(testChannel);
    }


    @Test
    @DisplayName("Получение всех каналов - успешно")
    @WithMockUser(roles = "USER")
    void getAllChannels_success() throws Exception {
        MvcResult result = mockMvc.perform(get(CHANNELS_BASE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<ChannelResponseDto> channels = List.of(
                objectMapper.readValue(result.getResponse().getContentAsString(), ChannelResponseDto[].class)
        );

        assertFalse(channels.isEmpty());
        ChannelResponseDto channel = channels.getFirst();
        assertEquals(testChannel.getId(), channel.id());
        assertEquals(testChannel.getName(), channel.name());
        assertEquals(testChannel.getDescription(), channel.description());
    }

    @Test
    @DisplayName("Получение канала по ID - неавторизован")
    void getChannelById_unauthorized() throws Exception {
        mockMvc.perform(get(CHANNELS_BASE + "/" + testChannel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(AUTHENTICATION_REQUIRED));
    }

    @Test
    @DisplayName("Получение канала по ID - успешно")
    @WithMockUser(roles = "USER")
    void getChannelById_success() throws Exception {
        MvcResult result = mockMvc.perform(get(CHANNELS_BASE + "/" + testChannel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ChannelResponseDto channel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ChannelResponseDto.class
        );

        assertEquals(testChannel.getId(), channel.id());
        assertEquals(testChannel.getName(), channel.name());
        assertEquals(testChannel.getDescription(), channel.description());
    }

    @Test
    @DisplayName("Получение канала по ID - канал не найден")
    @WithMockUser(roles = "USER")
    void getChannelById_notFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get(CHANNELS_BASE + "/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(CHANNEL_NOT_FOUND));
    }

    @Test
    @DisplayName("Получение канала по ID - некорректный UUID")
    @WithMockUser(roles = "USER")
    void getChannelById_invalidUuid() throws Exception {
        mockMvc.perform(get(CHANNELS_BASE + "/invalid-uuid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(INVALID_UUID));
    }
} 