package com.worch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserSessionException extends RuntimeException {
    public UserSessionException(String message) {
        super(message);
    }

    public UserSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
