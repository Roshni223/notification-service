package com.example.demo.service;

import com.example.demo.avro.OrderCreated;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DLQConsumer {

    @KafkaListener(topics = "order-dlq", groupId = "dlq-group")
    public void listen(OrderCreated order) {
        System.out.println("DLQ received order: " + order.getOrderId());
    }
}
