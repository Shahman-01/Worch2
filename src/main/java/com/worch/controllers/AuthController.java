package com.worch.controllers;

import com.worch.model.dto.request.LoginRequest;
import com.worch.model.dto.request.LogoutRequest;
import com.worch.model.dto.request.RegisterRequest;
import com.worch.model.dto.response.ErrorResponse;
import com.worch.model.dto.response.LoginResponse;
import com.worch.model.dto.response.SuccessResponse;
import com.worch.service.AuthService;
import com.worch.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создаёт нового пользователя в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse("Регистрация прошла успешно"));
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Выполняет вход пользователя и возвращает access и refresh токены"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Выход пользователя",
            description = "Удаляет refresh токен пользователя и завершает сессию"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно вышел",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(@RequestBody @Valid LogoutRequest request) {
        sessionService.logout(request.refreshToken());
        return ResponseEntity
                .ok(new SuccessResponse("Вы успешно вышли из системы"));
    }
}
