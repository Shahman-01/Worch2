package com.worch.exceptions;

import lombok.Getter;

@Getter
public class KeycloakOperationException extends RuntimeException {
    private final int statusCode;

    public KeycloakOperationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}