package com.worch.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectUuidException extends RuntimeException {
    public IncorrectUuidException(String message) {
        super(message);
    }
}
