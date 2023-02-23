package com.rremiao.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDTO {
    
    private Integer orderId;
    private String userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
