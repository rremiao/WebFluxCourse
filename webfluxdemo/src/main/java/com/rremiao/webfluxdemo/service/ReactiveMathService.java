package com.rremiao.webfluxdemo.service;

import org.springframework.stereotype.Service;

import com.rremiao.webfluxdemo.dto.MultiplyRequestDTO;
import com.rremiao.webfluxdemo.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {
    
    public Mono<Response> findSquare(int number) {
        return Mono.fromSupplier(() -> number * number).map(Response::new);
    }

    public Flux<Response> multiplicationTable(int number) {
        // List<Response> list = IntStream.rangeClosed(1, 10)
        //                                .peek(i -> SleepUtility.sleepSeconds(1))
        //                                .peek(i -> System.out.println("math-service processing: " + i))
        //                                .mapToObj(i -> new Response(i * number))
        //                                .collect(Collectors.toList());
        
        // return Flux.fromIterable(list);

        return Flux.range(1, 10)
                    .doOnNext(i -> SleepUtility.sleepSeconds(1))
                    .doOnNext(i -> System.out.println("reactive-math-service processing: " + i))
                    .map(i -> new Response(i * number));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDTO> dtoMono) {
        return dtoMono.map(dto -> dto.getFirst() * dto.getSecond()).map(Response::new);
    }
}
