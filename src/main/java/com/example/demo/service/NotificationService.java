package com.example.demo.service;

import com.example.demo.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @KafkaListener(topics = "order-topic", groupId = "notification-service")
    public void consume(Order order){
        System.out.println("order is picked from kafka with id " + order.orderId());
    }
}
