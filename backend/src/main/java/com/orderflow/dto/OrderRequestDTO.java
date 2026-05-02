package com.orderflow.dto;

import com.orderflow.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private Long userId;
    private Long productId;
    private Integer quantity;
    private OrderStatus status;
    private String requestId;
}