package com.rremiao.userservice.util;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.rremiao.userservice.dto.TransactionRequestDTO;
import com.rremiao.userservice.dto.TransactionResponseDTO;
import com.rremiao.userservice.dto.TransactionStatus;
import com.rremiao.userservice.dto.UserDTO;
import com.rremiao.userservice.entity.User;
import com.rremiao.userservice.entity.UserTransaction;

public class EntityDTOUtil {
    public static UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(user, userDTO);

        return userDTO;
    }

    public static User userToEntity(UserDTO userDTO) {
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(userDTO, user);

        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDTO transactionRequestDTO) {
        UserTransaction userTransaction = new UserTransaction();
        
        userTransaction.setUserId(transactionRequestDTO.getUserId());
        userTransaction.setAmount(transactionRequestDTO.getAmount());
        userTransaction.setTransactiondate(LocalDateTime.now());

        return userTransaction;
    }

    public static TransactionResponseDTO toDTO(TransactionRequestDTO transactionRequestDTO, TransactionStatus status) {
        TransactionResponseDTO response = new TransactionResponseDTO();

        response.setAmount(transactionRequestDTO.getAmount());
        response.setUserId(transactionRequestDTO.getUserId());
        response.setTransactionStatus(status);

        return response;
    }
}
