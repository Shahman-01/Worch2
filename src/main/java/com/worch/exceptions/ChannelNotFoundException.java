package com.worch.exceptions;

public class ChannelNotFoundException extends RuntimeException{
    public ChannelNotFoundException(String message) {
        super(message);
    }
}
