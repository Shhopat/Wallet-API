package com.example.test.util;

public class UnsupportedOperationException extends RuntimeException {
    public UnsupportedOperationException(String operationType) {
        super("Unsupported operation type: " + operationType + ".");
    }
}
