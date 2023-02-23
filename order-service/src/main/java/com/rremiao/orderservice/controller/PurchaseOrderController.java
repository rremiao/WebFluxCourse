package com.rremiao.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.rremiao.orderservice.dto.PurchaseOrderRequestDTO;
import com.rremiao.orderservice.dto.PurchaseOrderResponseDTO;
import com.rremiao.orderservice.service.OrderFulfillmentService;
import com.rremiao.orderservice.service.OrderQueryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    
    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService queryService;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDTO>> order(@RequestBody Mono<PurchaseOrderRequestDTO> requestDTO) {
        return orderFulfillmentService.processOrder(requestDTO)
                                      .map(ResponseEntity::ok)
                                      .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                                      .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDTO> getOrdersByUserID(@PathVariable String id) {
        return queryService.getProductsByUserId(id);
    }
}
