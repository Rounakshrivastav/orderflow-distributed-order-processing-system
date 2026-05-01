package com.orderflow.service;

import com.orderflow.dto.OrderRequestDTO;
import com.orderflow.dto.OrderResponseDTO;
import com.orderflow.entity.Order;
import com.orderflow.kafka.event.OrderEvent;
import com.orderflow.kafka.producer.OrderProducer;
import com.orderflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus("CREATED");

        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrderEvent(
                new OrderEvent(order.getId(), order.getStatus()));

        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getStatus());
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderResponseDTO getOrderById(Long id) {

        return orderRepository.findById(id)
                .map(order -> new OrderResponseDTO(order.getId(), order.getStatus()))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @CacheEvict(value = "orders", key = "#id")
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return new OrderResponseDTO(updatedOrder.getId(), updatedOrder.getStatus());
    }
}