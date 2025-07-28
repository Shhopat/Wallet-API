package com.example.test.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "balance", nullable = false)
    private Long balance;


}
