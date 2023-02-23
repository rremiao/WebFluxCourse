package com.rremiao.webfluxdemo.config;

import java.util.function.BiFunction;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {
    
    public Mono<ServerResponse> additionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a,b) ->
            b != 0 ? ServerResponse.ok().bodyValue(a / b) 
                   : ServerResponse.badRequest().bodyValue("b cannot be 0")
        );
    }

    public Mono<ServerResponse> process(ServerRequest serverRequest, BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
        int a = getValue(serverRequest, "a");
        int b = getValue(serverRequest, "b");

        return opLogic.apply(a, b);
    }

    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
