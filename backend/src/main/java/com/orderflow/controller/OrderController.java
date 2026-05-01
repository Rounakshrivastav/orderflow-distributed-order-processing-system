package com.orderflow.controller;

import com.orderflow.dto.OrderRequestDTO;
import com.orderflow.dto.OrderResponseDTO;
import com.orderflow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}