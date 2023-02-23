package com.rremiao.webfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.webfluxdemo.dto.Response;
import com.rremiao.webfluxdemo.exception.InputValidationException;
import com.rremiao.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    ReactiveMathService reactiveMathService;

    @GetMapping("square/{number}/throw")
    public Mono<Response> findSquare(@PathVariable int number) {
        if(number < 10 || number > 20) {
            throw new InputValidationException(number);
        }

        return reactiveMathService.findSquare(number);
    }

    @GetMapping("square/{number}/mono-error")
    public Mono<Response> monoError(@PathVariable int number) {
        return Mono.just(number)
                   .handle((integer, sink) -> {
                        if(integer >= 10 && integer <= 20)
                            sink.next(integer);
                        else 
                            sink.error(new InputValidationException(number));
                   })
                    .cast(Integer.class)
                    .flatMap(i -> reactiveMathService.findSquare(i));
    }   

    @GetMapping("square/{number}/assignment")
    public Mono<ResponseEntity<Response>> assigment(@PathVariable int number) {
        return Mono.just(number)
                   .filter(i -> i >= 10 && i <= 20)
                   .flatMap(i -> reactiveMathService.findSquare(i))
                   .map(ResponseEntity::ok)
                   .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
