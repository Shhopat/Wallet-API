package com.example.test.controllers;

import com.example.test.exception.WalletNotFoundException;
import com.example.test.models.Wallet;
import com.example.test.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    private Wallet wallet;

    @BeforeEach
    @Transactional
    public void setup() {
        wallet = new Wallet();
        wallet.setBalance(500L);
        walletRepository.save(wallet);
    }

    @Test
    public void shouldReturnWalletBalance() throws Exception {
        mockMvc.perform(get("/api/v1/wallets/" + wallet.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("500"));
    }

    @Test
    public void shouldDepositBalance() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + wallet.getId() + "\","
                + "\"operationType\": \"DEPOSIT\","
                + "\"amount\": 500"
                + "}";

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        Wallet result = walletRepository.findById(
                wallet.getId()).orElseThrow(WalletNotFoundException::new);
        assertEquals(1000L, result.getBalance());
    }

    @Test
    public void shouldWithdrawBalance() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + wallet.getId() + "\","
                + "\"operationType\": \"WITHDRAW\","
                + "\"amount\": 500"
                + "}";


        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        Wallet result = walletRepository.findById(
                wallet.getId()).orElseThrow(WalletNotFoundException::new);
        assertEquals(0L, result.getBalance());
    }

    @Test
    public void shouldThrowInvalidAmountException() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + wallet.getId() + "\","
                + "\"operationType\": \"WITHDRAW\","
                + "\"amount\": -500"
                + "}";

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void shouldThrowNotEnoughBalanceException() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + wallet.getId() + "\","
                + "\"operationType\": \"WITHDRAW\","
                + "\"amount\": 5000"
                + "}";
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowUnsupportedOperationException() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + wallet.getId() + "\","
                + "\"operationType\": \"WITH\","
                + "\"amount\": 500"
                + "}";

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowWalletNotFoundException() throws Exception {
        String request = "{"
                + "\"walletId\": \"" + UUID.randomUUID() + "\","
                + "\"operationType\": \"WITH\","
                + "\"amount\": 500"
                + "}";

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is4xxClientError());
    }


}
