package com.rremiao.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDTO {
    
    private String userId;
    private Integer amount;
    private TransactionStatus transactionStatus;

}
