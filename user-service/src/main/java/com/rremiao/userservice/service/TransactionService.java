package com.rremiao.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.userservice.dto.TransactionRequestDTO;
import com.rremiao.userservice.dto.TransactionResponseDTO;
import com.rremiao.userservice.dto.TransactionStatus;
import com.rremiao.userservice.entity.User;
import com.rremiao.userservice.entity.UserTransaction;
import com.rremiao.userservice.repository.UserRepository;
import com.rremiao.userservice.repository.UserTransactionRepository;
import com.rremiao.userservice.util.EntityDTOUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDTO> createTransaction(final TransactionRequestDTO requestDTO) {
        Mono<User> user = userRepository.findById(requestDTO.getUserId());

        return user.filter(u -> u.getBalance() > requestDTO.getAmount())
                   .doOnNext(s -> s.setBalance(s.getBalance() - requestDTO.getAmount()))
                   .flatMap(userRepository::save)
                   .map(b -> EntityDTOUtil.toEntity(requestDTO))
                   .flatMap(transactionRepository::save)
                   .map(ut -> EntityDTOUtil.toDTO(requestDTO, TransactionStatus.APPROVED))
                   .defaultIfEmpty(EntityDTOUtil.toDTO(requestDTO, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(String user) {
        return transactionRepository.findByUserId(user);
    }
}
