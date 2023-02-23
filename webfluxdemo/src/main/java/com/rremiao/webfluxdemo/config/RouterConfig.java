package com.rremiao.webfluxdemo.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.rremiao.webfluxdemo.dto.InputFailedValidationResponse;
import com.rremiao.webfluxdemo.exception.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                             .path("router", this::serverResponseFunction)
                             .build();

    }
    
    // @Bean
    public RouterFunction<ServerResponse> serverResponseFunction() {
        return RouterFunctions.route()
                             .GET("square/{number}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler)
                             .GET("square/{number}", req -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                             .GET("table/{number}", requestHandler::tableHandler)
                             .GET("table/{number}/stream", requestHandler::tableStreamHandler)
                             .POST("multiply", requestHandler::multiplyHandler)
                             .GET("square/{number}/validation", requestHandler::squareHandlerWithValidation)
                             .onError(InputValidationException.class, exceptionHandler())
                             .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, request) -> {
            InputValidationException ex = (InputValidationException) error;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());
            
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
