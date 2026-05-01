package com.orderflow.kafka.producer;

import com.orderflow.kafka.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "order-events";

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}