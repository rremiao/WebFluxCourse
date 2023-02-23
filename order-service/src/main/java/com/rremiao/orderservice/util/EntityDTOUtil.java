package com.rremiao.orderservice.util;

import org.modelmapper.ModelMapper;

import com.rremiao.orderservice.dto.OrderStatus;
import com.rremiao.orderservice.dto.PurchaseOrderResponseDTO;
import com.rremiao.orderservice.dto.RequestContext;
import com.rremiao.orderservice.dto.TransactionRequestDTO;
import com.rremiao.orderservice.dto.TransactionStatus;
import com.rremiao.orderservice.entity.PurchaseOrder;

public class EntityDTOUtil {

    public static PurchaseOrderResponseDTO getPurchaseOrderResponseDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDTO dto = new PurchaseOrderResponseDTO();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(purchaseOrder, dto);

        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }
    
    public static void setTransactionRequestDTO(RequestContext rc) {
        TransactionRequestDTO dto = new TransactionRequestDTO();

        dto.setUserId(rc.getPurchaseOrderRequestDTO().getUserId());
        dto.setAmount(rc.getProductDTO().getPrice());
        rc.setTransactionRequestDTO(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext rc) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setUserId(rc.getTransactionRequestDTO().getUserId());
        purchaseOrder.setProductId(rc.getPurchaseOrderRequestDTO().getProductId());
        purchaseOrder.setAmount(rc.getProductDTO().getPrice());

        TransactionStatus status = rc.getTransactionResponseDTO().getTransactionStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;

        purchaseOrder.setStatus(orderStatus);

        return purchaseOrder;
    }

}
