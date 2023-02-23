package com.rremiao.userservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.rremiao.userservice.entity.UserTransaction;

import reactor.core.publisher.Flux;


@Repository
public interface UserTransactionRepository extends ReactiveMongoRepository<UserTransaction, String>{

    Flux<UserTransaction> findByUserId(String userId);

}
