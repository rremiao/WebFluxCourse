package com.rremiao.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.productservice.dto.ProductDTO;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
public class ProductStreamController {
    
    @Autowired
    private Flux<ProductDTO> flux;

    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductUpdates() {
        return this.flux;
    }
}
