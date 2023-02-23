package com.rremiao.productservice.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.productservice.dto.ProductDTO;
import com.rremiao.productservice.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDTO> all() {
        return this.productService.getAll();
    }

    @GetMapping("price-range")
    public Flux<ProductDTO> priceRange(@RequestParam int min,
                                       @RequestParam int max) {

        return this.productService.getProductByPriceRange(min, max);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> getById(@PathVariable String id) {
        this.simulateRandomException();
        return this.productService.getProductById(id)
                                  .map(ResponseEntity::ok)
                                  .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDTO> insertProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        return this.productService.insertProduct(productDTOMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDTO> productDTOMono) {
        return this.productService.updateProduct(id, productDTOMono)
                                  .map(ResponseEntity::ok)
                                  .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return this.productService.deleteProduct(id);
    }

    private void simulateRandomException() {
        int nextInt = ThreadLocalRandom.current().nextInt(1, 10);

        if(nextInt > 5) {
            throw new RuntimeException("something is wrong");
        }
    }
}
