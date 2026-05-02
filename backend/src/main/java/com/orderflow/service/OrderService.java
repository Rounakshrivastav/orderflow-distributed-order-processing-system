package com.orderflow.service;

import com.orderflow.dto.OrderRequestDTO;
import com.orderflow.dto.OrderResponseDTO;
import com.orderflow.entity.Order;
import com.orderflow.entity.OrderStatus;
import com.orderflow.exception.OrderNotFoundException;
import com.orderflow.kafka.event.OrderEvent;
import com.orderflow.kafka.producer.OrderProducer;
import com.orderflow.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

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

        Optional<Order> existing = orderRepository.findByRequestId(request.getRequestId());

        if (existing.isPresent()) {
            return mapToDTO(existing.get());
        }

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.CREATED);
        order.setRequestId(request.getRequestId());

        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrderEvent(
                new OrderEvent(order.getId(), order.getStatus()));

        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getStatus());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderResponseDTO getOrderById(Long id) {

        return orderRepository.findById(id)
                .map(order -> new OrderResponseDTO(order.getId(), order.getStatus()))
                .orElseThrow(() -> new OrderNotFoundException("Order not found "));
    }

    @CacheEvict(value = "orders", key = "#id")
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found "));

        order.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return new OrderResponseDTO(updatedOrder.getId(), updatedOrder.getStatus());
    }

    private OrderResponseDTO mapToDTO(Order order) {
    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setOrderId(order.getId());
    dto.setStatus(order.getStatus());
    return dto;
}
}