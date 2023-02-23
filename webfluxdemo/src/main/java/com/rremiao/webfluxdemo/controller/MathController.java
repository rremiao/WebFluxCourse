package com.rremiao.webfluxdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.webfluxdemo.dto.Response;
import com.rremiao.webfluxdemo.service.MathService;

@RestController
@RequestMapping("math")
public class MathController {
    
    @Autowired
    private MathService mathService;

    @GetMapping("square/{number}")
    public Response findSquare(@PathVariable int number) {
        return mathService.findSquare(number);
    }

    @GetMapping("table/{number}")
    public List<Response> multiplicationTable(@PathVariable int number) {
        return mathService.multiplicationTable(number);
    }

}
