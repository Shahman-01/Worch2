package com.worch.exceptions;

public class StackNotFoundException extends RuntimeException {
    public StackNotFoundException(String message) {
        super(message);
    }
}
