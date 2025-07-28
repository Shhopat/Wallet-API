package com.example.test.controllers;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<HttpStatus> operate(@RequestBody WalletOperationDTO walletOperationDTO) {
        walletService.operate(walletOperationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
