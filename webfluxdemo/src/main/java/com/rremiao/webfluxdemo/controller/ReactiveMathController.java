package com.rremiao.webfluxdemo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.webfluxdemo.dto.MultiplyRequestDTO;
import com.rremiao.webfluxdemo.dto.Response;
import com.rremiao.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    
    @Autowired
    ReactiveMathService reactiveMathService;

    @GetMapping("square/{number}")
    public Mono<Response> findSquare(@PathVariable int number) {
        return reactiveMathService.findSquare(number);
    }

    @GetMapping("table/{number}")
    public Flux<Response> multiplicationTable(@PathVariable int number) {
        return reactiveMathService.multiplicationTable(number);
    }

    @GetMapping(value = "table/{number}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int number) {
        return reactiveMathService.multiplicationTable(number);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDTO> dto, @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return reactiveMathService.multiply(dto);
    }
}
