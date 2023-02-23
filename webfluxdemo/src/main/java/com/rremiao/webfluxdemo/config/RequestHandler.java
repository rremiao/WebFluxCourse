package com.rremiao.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rremiao.webfluxdemo.dto.MultiplyRequestDTO;
import com.rremiao.webfluxdemo.dto.Response;
import com.rremiao.webfluxdemo.exception.InputValidationException;
import com.rremiao.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int number = Integer.parseInt(serverRequest.pathVariable("number"));
        Mono<Response> responseMono = mathService.findSquare(number);

        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int number = Integer.parseInt(serverRequest.pathVariable("number"));
        Flux<Response> responseFlux = mathService.multiplicationTable(number);

        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int number = Integer.parseInt(serverRequest.pathVariable("number"));
        Flux<Response> responseFlux = mathService.multiplicationTable(number);

        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequestDTO> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDTO.class);
        Mono<Response> monoResponse = mathService.multiply(requestDtoMono);

        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(monoResponse, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int number = Integer.parseInt(serverRequest.pathVariable("number"));
        if(number < 10 || number > 20)
            return Mono.error(new InputValidationException(number));
        Mono<Response> responseMono = mathService.findSquare(number);

        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
