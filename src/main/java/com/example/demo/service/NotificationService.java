package com.example.demo.service;

import com.example.demo.model.Order;
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
    public void consume(Order order){
        if (processEventRepo.existsById(order.getOrderId())){
            return;
        }
        if (order.getProduct().equals("FAIL")) {
            throw new RuntimeException("Simulated failure");
        }
        System.out.println("order is picked from kafka with id " + order.getOrderId());
        processEventRepo.save(new ProcessedEvent(order.getOrderId()));
    }
}
