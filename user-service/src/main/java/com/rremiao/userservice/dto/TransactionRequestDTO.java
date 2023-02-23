package com.rremiao.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionRequestDTO {
    
    private String userId;
    private Integer amount;

}
