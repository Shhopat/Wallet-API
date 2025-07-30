package com.example.test.controllers;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.mapper.WalletMapper;
import com.example.test.service.WalletService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {
    private final WalletMapper walletMapper;
    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<HttpStatus> operate(@RequestBody WalletOperationDTO walletOperationDTO) {
        walletService.operate(walletOperationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public ResponseEntity<Long> getWallet(@PathVariable("WALLET_UUID") UUID walletId) {
        return new ResponseEntity<>(walletService.getBalance(walletId), HttpStatus.OK);

    }
}
