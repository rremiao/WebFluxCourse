package com.rremiao.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rremiao.orderservice.dto.TransactionRequestDTO;
import com.rremiao.orderservice.dto.TransactionResponseDTO;
import com.rremiao.orderservice.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    
    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url) {
        this.webClient = WebClient.builder()
                                  .baseUrl(url)
                                  .build();
    }

    public Mono<TransactionResponseDTO> authorizeTransaction(TransactionRequestDTO request) {
        return this.webClient.post()
                             .uri("transaction")
                             .bodyValue(request)
                             .retrieve()
                             .bodyToMono(TransactionResponseDTO.class);
    }

    public Flux<UserDTO> getAllUsers() {
        return this.webClient
                            .get()
                            .uri("all")
                            .retrieve()
                            .bodyToFlux(UserDTO.class);
    }
}
