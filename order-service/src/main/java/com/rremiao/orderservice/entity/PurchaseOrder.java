package com.rremiao.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.rremiao.orderservice.dto.OrderStatus;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class PurchaseOrder {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String productId;
    private String userId;
    private Integer amount;
    private OrderStatus status;
}
