package com.rremiao.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rremiao.userservice.dto.UserDTO;
import com.rremiao.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("all")
    public Flux<UserDTO> all() {
        return userService.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable String id) {
        return userService.getById(id)
                          .map(ResponseEntity::ok)
                          .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody Mono<UserDTO> userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable String id, @RequestBody Mono<UserDTO> userDTO) {
        return userService.updateUser(id, userDTO)
                          .map(ResponseEntity::ok)
                          .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

}
