package com.example.test.util;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("The amount cannot be negative or 0.");
    }
}
