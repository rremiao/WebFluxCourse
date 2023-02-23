package com.rremiao.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionRequestDTO {
    
    private String userId;
    private Integer amount;

}
