package com.orderflow.service;

import com.orderflow.dto.OrderRequestDTO;
import com.orderflow.dto.OrderResponseDTO;
import com.orderflow.entity.Order;
import com.orderflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus("CREATED");

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getStatus());
    }
    @Cacheable(value = "orders", key = "#id")
    public OrderResponseDTO getOrderById(Long id) {

    return orderRepository.findById(id)
            .map(order -> new OrderResponseDTO(order.getId(), order.getStatus()))
            .orElseThrow(() -> new RuntimeException("Order not found"));
}
}