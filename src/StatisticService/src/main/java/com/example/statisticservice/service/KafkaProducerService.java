package com.example.statisticservice.service;

import lombok.NoArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@NoArgsConstructor

@Service
public class KafkaProducerService {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//    public void sendRequestInfo(String requestType, long executionTime) {
//        String message = "Request Type: " + requestType + ", Execution Time: " + executionTime + "ms";
//        kafkaTemplate.send("request-info-topic", message);
//    }
}