package com.rremiao.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rremiao.productservice.dto.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<ProductDTO> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDTO> productBroadcast(Sinks.Many<ProductDTO> sink) {
        return sink.asFlux();
    }
}
