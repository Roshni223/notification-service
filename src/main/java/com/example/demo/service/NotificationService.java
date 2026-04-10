package com.example.demo.service;

import com.example.demo.avro.OrderCreated;
import com.example.demo.model.ProcessedEvent;
import com.example.demo.repository.ProcessEventRepo;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final ProcessEventRepo processEventRepo;
    NotificationService(ProcessEventRepo processEventRepo){
        this.processEventRepo = processEventRepo;
    }

    @KafkaListener(topics = "order-topic", groupId = "notification-service")
    @Transactional
    public void consume(OrderCreated order){
        String orderId = order.getOrderId();
        if (processEventRepo.existsById(orderId)){
            return;
        }
        if ("FAIL".equals(order.getProduct())) {
            throw new RuntimeException("Simulated failure");
        }
        System.out.println("order is picked from kafka with id " + orderId);
        processEventRepo.save(new ProcessedEvent(orderId));
    }
}
