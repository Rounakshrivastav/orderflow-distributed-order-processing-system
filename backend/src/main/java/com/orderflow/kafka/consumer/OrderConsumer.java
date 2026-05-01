package com.orderflow.kafka.consumer;

import com.orderflow.kafka.event.OrderEvent;
import com.orderflow.repository.OrderRepository;
import com.orderflow.entity.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

     @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CacheManager cacheManager;

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consume(OrderEvent event) {

        System.out.println("Processing order: " + event);

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // simulate processing
        order.setStatus("COMPLETED");
        orderRepository.save(order);

        // 🔥 IMPORTANT: evict cache after update
        cacheManager.getCache("orders").evict(event.getOrderId());

        System.out.println("Order completed: " + order.getId());
    }
}