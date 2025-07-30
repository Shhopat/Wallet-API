package com.example.test.exception;

public class UnsupportedOperationException extends RuntimeException {
    public UnsupportedOperationException(String operationType) {
        super("Unsupported operation type: " + operationType + ".");
    }
}
