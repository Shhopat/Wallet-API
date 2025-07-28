package com.example.test.dto;

import com.example.test.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOperationDTO {
    private UUID walletId;
    private OperationType operationType;
    private Long amount;
}
