package com.example.test.exception;

import com.example.test.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleInvalidAmount(InvalidAmountException invalidAmountException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(invalidAmountException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleNotEnoughBalanceException(NotEnoughBalanceException notEnoughBalanceException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(notEnoughBalanceException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleUnsupportedOperationException(UnsupportedOperationException unsupportedOperationException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(unsupportedOperationException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleWalletNotFoundException(WalletNotFoundException walletNotFoundException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(walletNotFoundException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Invalid operationType value. Allowed values: DEPOSIT, WITHDRAW.", LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }


}
