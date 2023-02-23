package com.rremiao.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.core.publisher.Flux;

public class Lec09AssingmentTest extends BaseTest{

    private final static String FORMAT = "%d %s %d = %s";
    private final int A = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void test(){
       Flux<String> fluxString = Flux.range(1, 5)
                                     .flatMap(b -> Flux.just("+", "-", "*", "/")
                                     .flatMap(op -> send(b, op)))
                                     .doOnNext(System.out::println);
 
        
        StepVerifier.create(fluxString)
                    .expectNextCount(20)
                    .verifyComplete();

    }

    private Mono<String> send(int b, String op) {
        return this.webClient
                        .get()
                        .uri("calculator/{a}/{b}", A, b)
                        .headers(h -> h.set("OP", op))
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(v -> String.format(FORMAT, A, op, b, v));
    }




    
}
