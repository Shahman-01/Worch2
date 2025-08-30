package com.worch.tests.service;

import com.worch.mapper.UserMapper;
import com.worch.model.dto.response.UserProfileResponse;
import com.worch.model.entity.User;
import com.worch.repository.UserRepository;
import com.worch.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toUserProfileResponse(user)).thenReturn(
                new UserProfileResponse(
                        id,null, null, null, null, null, null
                )
        );

        User result = userService.getUserById(id.toString());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(userRepository).findById(id);
    }
}
