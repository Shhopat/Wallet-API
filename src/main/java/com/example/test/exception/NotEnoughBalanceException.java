package com.example.test.exception;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException() {
        super("Not enough balance.");
    }
}
