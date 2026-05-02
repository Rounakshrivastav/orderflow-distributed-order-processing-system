package com.orderflow.dto;

import java.io.Serializable;

import com.orderflow.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO implements Serializable {


    private Long orderId;
    private OrderStatus status;
}