package com.reserva.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void publish(String topic, String key, Object event) {
        kafkaTemplate.send(topic, key, event);
    }
}