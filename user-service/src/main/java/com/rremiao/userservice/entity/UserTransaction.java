package com.rremiao.userservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserTransaction {
    
    @Id
    private String id;
    private String userId;
    private Integer amount;
    private LocalDateTime transactiondate;
}
