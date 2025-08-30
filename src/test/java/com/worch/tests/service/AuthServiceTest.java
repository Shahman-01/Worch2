package com.worch.tests.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.worch.mapper.UserMapper;
import com.worch.model.dto.request.RegisterRequest;
import com.worch.model.entity.User;
import com.worch.repository.UserRepository;
import com.worch.service.AuthService;
import com.worch.service.KeycloakService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса авторизации")
class AuthServiceTest {

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Регистрация — корректный запрос с email создает пользователя")
    void register_validRequestWithEmail_createsUserWithEmail() {
        RegisterRequest request = new RegisterRequest(
                "+79991234567",
                "testuser",
                "Qwerty123!",
                "test@mail.com",
                "Test",
                "User"
        );

        when(userRepository.existsByLogin("testuser")).thenReturn(false);

        User mappedUser = new User();
        mappedUser.setLogin("testuser");
        mappedUser.setEmail("test@mail.com");
        mappedUser.setFirstName("Test");
        mappedUser.setLastName("User");
        when(userMapper.toEntity(request)).thenReturn(mappedUser);

        authService.register(request);

        verify(keycloakService).createUser(request);
        verify(userRepository).save(mappedUser);
    }
}
