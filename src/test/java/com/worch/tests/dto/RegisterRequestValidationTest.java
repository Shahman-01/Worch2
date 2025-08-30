package com.worch.tests.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.worch.model.dto.request.RegisterRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Валидация запроса регистрации")
class RegisterRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static Stream<RegisterRequest> validRequests() {
        return Stream.of(
                new RegisterRequest("+79991234567", "user1", "Pass123!", "user@mail.com", "Ivan", "Ivanov"),
                new RegisterRequest("+79991234567", "user2", "Pass123!", "test@mail.com", "Ivan", "Ivanov"),
                new RegisterRequest("+7", "a", "1!", "test@mail.com", "A", "B")
        );
    }

    static Stream<RegisterRequest> invalidRequests() {
        return Stream.of(
                new RegisterRequest(null, null, null, null, null, null),
                new RegisterRequest("+79991234567", "user", "Pass123!", "invalid_email", "Ivan", "Ivanov"),
                new RegisterRequest("+79991234567", "user", "", null, "Ivan", "Ivanov"),
                new RegisterRequest("+79991234567", "user", "Pass123!", null, "", "Ivanov")
        );
    }

    @ParameterizedTest
    @MethodSource("validRequests")
    @DisplayName("Валидация — корректный запрос должен проходить")
    void validation_validRequest_shouldPass(RegisterRequest request) {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    @DisplayName("Валидация — некорректный запрос должен падать")
    void validation_invalidRequest_shouldFail(RegisterRequest request) {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations)
                .hasSizeGreaterThan(0)
                .allMatch(v -> v.getMessage().startsWith("Поле") || v.getMessage().startsWith("Некорректный"));
    }
}
