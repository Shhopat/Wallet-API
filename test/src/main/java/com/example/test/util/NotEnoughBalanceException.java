package com.example.test.util;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException() {
        super("Not enough balance.");
    }
}
