package com.rremiao.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDTO {
    
    private String userId;
    private Integer amount;
    private TransactionStatus transactionStatus;

}
