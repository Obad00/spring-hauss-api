package com.example.exception;

public class LogementNotFoundException extends RuntimeException {
    public LogementNotFoundException(String message) {
        super(message);
    }
}
