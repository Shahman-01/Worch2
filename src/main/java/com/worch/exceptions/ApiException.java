package com.worch.exceptions;


public class ApiException extends RuntimeException {
    public ApiException(String msg) {
        super(msg);
    }
}
