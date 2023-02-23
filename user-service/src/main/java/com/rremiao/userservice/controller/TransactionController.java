package com.rremiao.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.userservice.dto.TransactionRequestDTO;
import com.rremiao.userservice.dto.TransactionResponseDTO;
import com.rremiao.userservice.entity.UserTransaction;
import com.rremiao.userservice.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> createTransaction(@RequestBody Mono<TransactionRequestDTO> requestDTO) {
        return requestDTO.flatMap(transactionService::createTransaction);
    }

    @GetMapping()
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") String userId) {
        return transactionService.getByUserId(userId);
    }
}
