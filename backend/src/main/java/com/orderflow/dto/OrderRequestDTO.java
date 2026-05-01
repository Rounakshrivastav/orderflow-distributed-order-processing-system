package com.orderflow.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private Long userId;
    private Long productId;
    private Integer quantity;
    private String status;
}