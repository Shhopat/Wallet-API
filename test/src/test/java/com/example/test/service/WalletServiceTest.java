package com.example.test.service;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.enums.OperationType;
import com.example.test.models.Wallet;
import com.example.test.repository.WalletRepository;
import com.example.test.exception.InvalidAmountException;
import com.example.test.exception.NotEnoughBalanceException;
import com.example.test.exception.UnsupportedOperationException;
import com.example.test.exception.WalletNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;

    private WalletOperationDTO walletOperationDepositDTO;
    private WalletOperationDTO walletOperationWithdrawDTO;

    @BeforeEach
    public void init() {
        walletOperationDepositDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.DEPOSIT, 1000L);

        walletOperationWithdrawDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.WITHDRAW, 100L);

        wallet = new Wallet(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                500L);
    }

    @Test
    public void shouldOperateWithDeposit() {
        Mockito.when(walletRepository.findById(
                        walletOperationDepositDTO.getWalletId()))
                .thenReturn(Optional.of(wallet));

        walletService.operate(walletOperationDepositDTO);

        Assertions.assertEquals(1500L, wallet.getBalance());

        Mockito.verify(walletRepository).findById(walletOperationDepositDTO.getWalletId());
        Mockito.verify(walletRepository).save(wallet);
    }

    @Test
    public void shouldOperateWithWithdraw() {
        Mockito.when(walletRepository.findById(
                        walletOperationWithdrawDTO.getWalletId()))
                .thenReturn(Optional.of(wallet));

        walletService.operate(walletOperationWithdrawDTO);

        Assertions.assertEquals(400L, wallet.getBalance());

        Mockito.verify(walletRepository).findById(walletOperationWithdrawDTO.getWalletId());
        Mockito.verify(walletRepository).save(wallet);


    }

    @Test
    public void shouldThrowWalletNotFoundException() {
        Mockito.when(walletRepository.findById(walletOperationWithdrawDTO.getWalletId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(WalletNotFoundException.class, () -> walletService.operate(walletOperationWithdrawDTO));
    }

    @Test
    public void shouldThrowNotEnoughBalanceException() {
        Wallet walletNoEnoughBalance = new Wallet(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                50L);
        Mockito.when(walletRepository.findById(walletOperationWithdrawDTO.getWalletId()))
                .thenReturn(Optional.of(walletNoEnoughBalance));

        Assertions.assertThrows(NotEnoughBalanceException.class, () -> walletService.operate(walletOperationWithdrawDTO));
    }

    @Test
    public void shouldThrowUnsupportedOperationException() {
        WalletOperationDTO walletOperationTestDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.TEST, 1000L);

        Mockito.when(walletRepository.findById(walletOperationTestDTO.getWalletId()))
                .thenReturn(Optional.of(wallet));

        Assertions.assertThrows(UnsupportedOperationException.class, () -> walletService.operate(walletOperationTestDTO));

    }

    @Test
    public void shouldThrowInvalidAmountException() {
        WalletOperationDTO walletOperationTestDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.DEPOSIT, -1000L);
        Mockito.when(walletRepository.findById(walletOperationTestDTO.getWalletId()))
                .thenReturn(Optional.of(wallet));

        Assertions.assertThrows(InvalidAmountException.class, () -> walletService.operate(walletOperationTestDTO));

    }

    @Test
    public void shouldGetBalance() {
        Mockito.when(walletRepository.findById(walletOperationWithdrawDTO.getWalletId()))
                .thenReturn(Optional.of(wallet));
        Long balance = walletService.getBalance(walletOperationWithdrawDTO.getWalletId());

        Assertions.assertEquals(500L, balance);

        Mockito.verify(walletRepository).findById(walletOperationWithdrawDTO.getWalletId());

    }
}
