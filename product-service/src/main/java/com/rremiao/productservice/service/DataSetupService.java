package com.rremiao.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.rremiao.productservice.dto.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String ...args) throws Exception {
        ProductDTO p1 = new ProductDTO("4K TV", 1000);
        ProductDTO p2 = new ProductDTO("SLR CAMERA", 750);
        ProductDTO p3 = new ProductDTO("NOTEBOOK", 800);
        ProductDTO p4 = new ProductDTO("IPHONE", 100);

        Flux.just(p1, p2, p3, p4)
            .flatMap(p -> productService.insertProduct(Mono.just(p)))
            .subscribe(System.out::println);
    }
    
}
