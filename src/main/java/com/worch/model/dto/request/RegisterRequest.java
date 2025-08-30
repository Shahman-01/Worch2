package com.worch.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Поле 'phone' обязательно для заполнения")
        String phone,

        @NotBlank(message = "Поле 'login' обязательно для заполнения")
        String login,

        @NotBlank(message = "Поле 'password' обязательно для заполнения")
        String password,

        @Email(message = "Некорректный email")
        @NotBlank(message = "Поле 'email' обязательно для заполнения")
        String email,

        @NotBlank(message = "Поле 'firstName' обязательно для заполнения")
        String firstName,

        @NotBlank(message = "Поле 'lastName' обязательно для заполнения")
        String lastName
) {
}
