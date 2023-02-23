package com.rremiao.productservice.service;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.rremiao.productservice.dto.ProductDTO;
import com.rremiao.productservice.repository.ProductRepository;
import com.rremiao.productservice.util.EntityDTOUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository repository;

    @Autowired
    Sinks.Many<ProductDTO> sink;

    public Flux<ProductDTO> getAll() {
        return this.repository.findAll()
                              .map(EntityDTOUtil::toDto);
    }

    public Flux<ProductDTO> getProductByPriceRange(int min, int max) {
        return this.repository.findByPriceBetween(Range.closed(min, max))
                              .map(EntityDTOUtil::toDto)
                              .sort(Comparator.comparing(ProductDTO::getPrice));
    }

    public Mono<ProductDTO> getProductById(String id) {
        return this.repository.findById(id).map(EntityDTOUtil::toDto);
    }

    public Mono<ProductDTO> insertProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(EntityDTOUtil::toEntity)
                             .flatMap(this.repository::insert)
                             .map(EntityDTOUtil::toDto)
                             .doOnNext(this.sink::tryEmitNext);
    }

    public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTOMono) {
        return this.repository.findById(id)
                                .flatMap(p -> productDTOMono
                                                    .map(EntityDTOUtil::toEntity)
                                                    .doOnNext(e -> e.setId(id)))
                                .flatMap(this.repository::save)
                                .map(EntityDTOUtil::toDto);                 
    }   

    public Mono<Void> deleteProduct(String id) {
        return this.repository.deleteById(id);
    }
}
