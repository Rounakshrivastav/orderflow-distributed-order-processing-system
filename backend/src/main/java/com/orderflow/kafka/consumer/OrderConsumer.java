package com.orderflow.kafka.consumer;

import com.orderflow.kafka.event.OrderEvent;
import com.orderflow.repository.OrderRepository;
import com.orderflow.entity.Order;
import com.orderflow.exception.OrderNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CacheManager cacheManager;

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consume(OrderEvent event) {

        try {

            // if (event.getOrderId() % 2 == 0) {
            // throw new RuntimeException("Simulated failure");
            // }

            log.info("Processing order: {}", event);

            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found "));

            // simulate processing
            order.setStatus("COMPLETED");
            orderRepository.save(order);

            // evict cache after update
            cacheManager.getCache("orders").evict(event.getOrderId());

            log.info("Order completed: {}", order.getId());
        } catch (Exception e) {

            log.error("Error processing order: {}", event);
            throw e; // for retry
        }
    }

    @KafkaListener(topics = "order-events-dlq", groupId = "dlq-group")
    public void consumeDLQ(OrderEvent event) {
        log.error("DLQ event received: {}", event);
    }
}