package com.example.test.dto;

import com.example.test.enums.OperationType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletOperationDTO {
    private UUID walletId;
    private OperationType operationType;
    private Long amount;
}
