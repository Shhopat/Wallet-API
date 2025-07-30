package com.example.test.exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID  walletId) {
        super("Wallet not found with id: " + walletId + ".");
    }

    public WalletNotFoundException() {
        super("Wallet not found. ");
    }
}
