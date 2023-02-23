package com.rremiao.userservice.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    
    @Id
    private String id;
    private String name;
    private Integer balance;
}
