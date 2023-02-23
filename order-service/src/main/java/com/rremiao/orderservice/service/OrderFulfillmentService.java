package com.rremiao.orderservice.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.orderservice.client.ProductClient;
import com.rremiao.orderservice.client.UserClient;
import com.rremiao.orderservice.dto.PurchaseOrderRequestDTO;
import com.rremiao.orderservice.dto.PurchaseOrderResponseDTO;
import com.rremiao.orderservice.dto.RequestContext;
import com.rremiao.orderservice.repository.PurchaseOrderRepository;
import com.rremiao.orderservice.util.EntityDTOUtil;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentService {

    @Autowired
    PurchaseOrderRepository orderRepository;
    
    @Autowired
    ProductClient productClient;

    @Autowired
    UserClient userClient;

    public Mono<PurchaseOrderResponseDTO> processOrder(Mono<PurchaseOrderRequestDTO> request) {
        return request.map(RequestContext::new)
                      .flatMap(this::productRequestResponse)
                      .doOnNext(EntityDTOUtil::setTransactionRequestDTO)
                      .flatMap(this::userRequestResponse)
                      .map(EntityDTOUtil::getPurchaseOrder)
                      .map(orderRepository::save)
                      .map(EntityDTOUtil::getPurchaseOrderResponseDTO)
                      .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return productClient.getProductById(rc.getPurchaseOrderRequestDTO().getProductId())
                            .doOnNext(rc::setProductDTO)
                            .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                            .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return userClient.authorizeTransaction(rc.getTransactionRequestDTO())
                         .doOnNext(rc::setTransactionResponseDTO)
                         .thenReturn(rc);
    }
}
