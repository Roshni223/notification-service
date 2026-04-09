package com.example.demo.config;

import com.example.demo.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory(
            ConsumerFactory<String, Order> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Order> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        // Retry 3 times with 2 sec gap
        FixedBackOff backOff = new FixedBackOff(2000L, 3);

        factory.setCommonErrorHandler(new DefaultErrorHandler(
                (record, exception) -> {
                    // Send to DLQ manually
                    System.out.println("Sending to DLQ: " + record.value());
                },
                backOff
        ));

        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Order> kafkaTemplate) {

        FixedBackOff backOff = new FixedBackOff(2000L, 3);

        return new DefaultErrorHandler(
                (record, exception) -> {
                    kafkaTemplate.send("order-dlq", (Order) record.value());
                },
                backOff
        );
    }
}