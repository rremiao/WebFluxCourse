package com.rremiao.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDTO {
    
    private String userId;
    private String productId;
}
