package com.example.test.service;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.models.Wallet;
import com.example.test.repository.WalletRepository;
import com.example.test.exception.InvalidAmountException;
import com.example.test.exception.NotEnoughBalanceException;
import com.example.test.exception.UnsupportedOperationException;
import com.example.test.exception.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    @Transactional
    public Wallet findById(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }


    @Transactional
    public void operate(WalletOperationDTO walletOperationDTO) {
        Wallet wallet = findById(walletOperationDTO.getWalletId());
        if (walletOperationDTO.getAmount() <= 0) {
            throw new InvalidAmountException();
        }
        switch (walletOperationDTO.getOperationType()) {
            case DEPOSIT -> wallet.setBalance(wallet.getBalance() + walletOperationDTO.getAmount());
            case WITHDRAW -> {
                if (wallet.getBalance() < walletOperationDTO.getAmount()) {
                    throw new NotEnoughBalanceException();
                }
                wallet.setBalance(wallet.getBalance() - walletOperationDTO.getAmount());
            }
            default -> throw new UnsupportedOperationException(walletOperationDTO.getOperationType().toString());
        }
        walletRepository.save(wallet);
    }

    @Transactional
    public Long getBalance(UUID walletId) {
        return findById(walletId).getBalance();

    }
}
