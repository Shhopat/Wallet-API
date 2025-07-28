package com.example.test.mapper;

import com.example.test.dto.WalletOperationDTO;
import com.example.test.models.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    Wallet toEntity(WalletOperationDTO walletOperationDTO);
    WalletOperationDTO toDTO(Wallet wallet);
    List<Wallet> toEntityList(List<WalletOperationDTO> walletOperationDTOList);
    List<WalletOperationDTO> toDTOList(List<Wallet> walletList);

}
