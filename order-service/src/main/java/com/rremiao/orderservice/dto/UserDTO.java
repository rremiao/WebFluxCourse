package com.rremiao.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    
    private String id;
    private String name;
    private Integer balance;
}
