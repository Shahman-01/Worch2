package com.worch.tests.service;

import com.worch.model.dto.request.CreateChannelRequest;
import com.worch.model.dto.request.UpdateChannelRequest;
import com.worch.model.entity.Channel;
import com.worch.model.entity.User;
import com.worch.repository.ChannelRepository;
import com.worch.service.ChannelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса каналов")
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelService channelService;

    @Test
    void createChannel_shouldCreatePrivateChannelWithEncodedPassword() {
        CreateChannelRequest request = CreateChannelRequest.builder()
                .name("Private Channel")
                .description("Some description")
                .isPrivate(true)
                .password("Password")
                .ageRestricted(false)
                .owner(new User())
                .build();

        Channel expectedChannel = new Channel();
        expectedChannel.setName("Private Channel");

        when(passwordEncoder.encode("Password")).thenReturn("$encodedPassword");
        when(channelRepository.save(any(Channel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Channel result = channelService.createChannel(request);

        assertThat(result.getPassword()).isEqualTo("$encodedPassword");
        assertThat(result.getIsPrivate()).isTrue();
        verify(channelRepository).save(any(Channel.class));
    }

    @Test
    void createChannel_shouldCreatePublicChannelWithoutPassword() {
        CreateChannelRequest request = CreateChannelRequest.builder()
                .name("Public Channel")
                .description("Open for all")
                .isPrivate(false)
                .ageRestricted(false)
                .owner(new User())
                .build();

        when(channelRepository.save(any(Channel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Channel result = channelService.createChannel(request);

        assertThat(result.getIsPrivate()).isFalse();
        assertThat(result.getPassword()).isNull();
    }

    @Test
    void findChannel_shouldReturnChannelIfExists() {
        UUID id = UUID.randomUUID();
        Channel channel = new Channel();
        channel.setId(id);

        when(channelRepository.findById(id)).thenReturn(Optional.of(channel));

        Channel result = channelService.findChannel(id);

        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void updateChannel_shouldUpdateChannelFieldsAndEncodePassword() {
        UUID id = UUID.randomUUID();
        Channel existing = new Channel();
        existing.setId(id);
        existing.setName("Old name");

        UpdateChannelRequest request = UpdateChannelRequest.builder()
                .name("New name")
                .description("Updated")
                .isPrivate(true)
                .password("newPass")
                .ageRestricted(true)
                .build();

        when(channelRepository.findById(id)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(channelRepository.save(any(Channel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Channel result = channelService.updateChannel(id, request);

        assertThat(result.getName()).isEqualTo("New name");
        assertThat(result.getPassword()).isEqualTo("encodedNewPass");
    }

    @Test
    void deleteChannel_shouldCallRepositoryDelete() {
        UUID id = UUID.randomUUID();

        channelService.deleteChannel(id);

        verify(channelRepository).deleteById(id);
    }
}
