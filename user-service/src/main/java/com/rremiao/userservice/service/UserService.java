package com.rremiao.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.userservice.dto.UserDTO;
import com.rremiao.userservice.repository.UserRepository;
import com.rremiao.userservice.util.EntityDTOUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    
    @Autowired
    UserRepository repository;

    public Flux<UserDTO> all() {
        return this.repository.findAll()
                              .map(EntityDTOUtil::userToDTO);
    }

    public Mono<UserDTO> getById(final String userId) {
        return this.repository.findById(userId)
                              .map(EntityDTOUtil::userToDTO);

    }

    public Mono<UserDTO> createUser(Mono<UserDTO> userDTO) {
       return userDTO.map(EntityDTOUtil::userToEntity)
                     .flatMap(this.repository::save)
                     .map(EntityDTOUtil::userToDTO);
    }

    public Mono<UserDTO> updateUser(String id, Mono<UserDTO> userDTO) {
        return this.repository.findById(id)
                              .flatMap(u -> userDTO
                                                .map(EntityDTOUtil::userToEntity)
                                                .doOnNext(e -> e.setId(id)))
                              .flatMap(this.repository::save)
                              .map(EntityDTOUtil::userToDTO);
    }

    public Mono<Void> deleteUser(String id) {
        return this.repository.deleteById(id);
    }
}
