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
        Mockito.when(walletRepository.deposit(
                walletOperationDepositDTO.getAmount(),
                walletOperationDepositDTO.getWalletId())).thenReturn(1);

        walletService.operate(walletOperationDepositDTO);

        Mockito.verify(walletRepository).deposit(walletOperationDepositDTO.getAmount(), walletOperationDepositDTO.getWalletId());

    }

    @Test
    public void shouldOperateWithWithdraw() {
        Mockito.when(walletRepository.withdraw(
                walletOperationWithdrawDTO.getAmount(),
                walletOperationWithdrawDTO.getWalletId())).thenReturn(1);

        walletService.operate(walletOperationWithdrawDTO);

        Mockito.verify(walletRepository).withdraw(walletOperationWithdrawDTO.getAmount(), walletOperationWithdrawDTO.getWalletId());


    }

    @Test
    public void shouldThrowWalletNotFoundException() {
        Mockito.when(walletRepository.deposit(walletOperationDepositDTO.getAmount(),
                walletOperationDepositDTO.getWalletId())).thenReturn(0);

        Assertions.assertThrows(WalletNotFoundException.class, () -> walletService.operate(walletOperationDepositDTO));

        Mockito.verify(walletRepository).deposit(walletOperationDepositDTO.getAmount(), walletOperationDepositDTO.getWalletId());


    }

    @Test
    public void shouldThrowWalletNotFoundExceptionIfTypeWithdraw() {
        Mockito.when(walletRepository.withdraw(walletOperationWithdrawDTO.getAmount(),
                walletOperationWithdrawDTO.getWalletId())).thenReturn(0);
        Mockito.when(walletRepository.existsById(walletOperationWithdrawDTO.getWalletId())).thenReturn(false);

        Assertions.assertThrows(WalletNotFoundException.class, () -> walletService.operate(walletOperationWithdrawDTO));

        Mockito.verify(walletRepository).withdraw(walletOperationWithdrawDTO.getAmount(), walletOperationWithdrawDTO.getWalletId());
        Mockito.verify(walletRepository).existsById(walletOperationWithdrawDTO.getWalletId());


    }

    @Test
    public void shouldThrowUnsupportedOperationException() {
        WalletOperationDTO walletOperationTestDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.TEST, 1000L);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> walletService.operate(walletOperationTestDTO));

    }

    @Test
    public void shouldThrowInvalidAmountException() {
        WalletOperationDTO walletOperationTestDTO = new WalletOperationDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                OperationType.DEPOSIT, -100000L);

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
