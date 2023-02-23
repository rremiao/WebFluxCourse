package com.rremiao.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.orderservice.dto.PurchaseOrderResponseDTO;
import com.rremiao.orderservice.repository.PurchaseOrderRepository;
import com.rremiao.orderservice.util.EntityDTOUtil;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {
    
    @Autowired
    private PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDTO> getProductsByUserId(String userId) {
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream())
                        .map(EntityDTOUtil::getPurchaseOrderResponseDTO)
                        .subscribeOn(Schedulers.boundedElastic());
    }
}
