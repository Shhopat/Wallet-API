package com.example.test.mapper;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.models.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(source = "walletId", target = "id")
    @Mapping(source = "amount", target = "balance")
    Wallet toEntity(WalletOperationDTO walletOperationDTO);

    @Mapping(source = "id", target = "walletId")
    @Mapping(source = "balance", target = "amount")
    WalletOperationDTO toDTO(Wallet wallet);

    List<Wallet> toEntityList(List<WalletOperationDTO> walletOperationDTOList);

    List<WalletOperationDTO> toDTOList(List<Wallet> walletList);

}
