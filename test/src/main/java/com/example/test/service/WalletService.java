package com.example.test.service;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.enums.OperationType;
import com.example.test.models.Wallet;
import com.example.test.repository.WalletRepository;
import com.example.test.util.InvalidAmountException;
import com.example.test.util.NotEnoughBalanceException;
import com.example.test.util.UnsupportedOperationException;
import com.example.test.util.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    @Transactional
    public void operate(WalletOperationDTO walletOperationDTO) {
        Wallet wallet = walletRepository.findById(walletOperationDTO.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(walletOperationDTO.getWalletId()));
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
}
