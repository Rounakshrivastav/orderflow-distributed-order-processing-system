package com.orderflow.controller;

import com.orderflow.dto.OrderRequestDTO;
import com.orderflow.dto.OrderResponseDTO;
import com.orderflow.entity.Order;
import com.orderflow.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}