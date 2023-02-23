package com.rremiao.webfluxdemo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.rremiao.webfluxdemo.dto.Response;

@Service
public class MathService {
    
    public Response findSquare(int number) {
        return new Response(number * number);
    }

    public List<Response> multiplicationTable(int number) {
        return IntStream.rangeClosed(1, 10)
                        .peek(i -> SleepUtility.sleepSeconds(1))
                        .peek(i -> System.out.println("math-service processing: " + i))
                        .mapToObj(i -> new Response(i * number)).collect(Collectors.toList());
    }
}
