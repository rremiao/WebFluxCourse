package com.rremiao.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.rremiao.webfluxdemo.dto.MultiplyRequestDTO;
import com.rremiao.webfluxdemo.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        Mono<Response> mono = this.webClient
                                    .post()
                                    .uri("reactive-math/multiply")
                                    .bodyValue(buildMultiplyRequestDTO(5, 2))
                                    .headers(h -> h.set("some-key", "some-value"))
                                    // .headers(h -> h.setBasicAuth("username", "password"))
                                    .retrieve()
                                    .bodyToMono(Response.class)
                                    .doOnNext(System.out::println);

        StepVerifier.create(mono)
                    .expectNextCount(1)
                    .verifyComplete();

        System.out.println("--------------- MULTIPLY TEST COMPLETE --------------- ");
    }

    private MultiplyRequestDTO buildMultiplyRequestDTO(int a, int b) {
        MultiplyRequestDTO dto = new MultiplyRequestDTO();

        dto.setFirst(a);
        dto.setSecond(b);

        return dto;
    }
}
