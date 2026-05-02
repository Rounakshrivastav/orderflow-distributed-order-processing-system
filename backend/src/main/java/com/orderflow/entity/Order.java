package com.orderflow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
    private Integer quantity;
    @Column(unique = true)
    private String requestId;
    // private String status;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}