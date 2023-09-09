package com.example.statisticservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "your_topic_name", groupId = "your_group_id")
    public void consumeEvent(String event) {
        // Обработка полученного события статистики
        System.out.println("Received event: " + event);
    }
}
