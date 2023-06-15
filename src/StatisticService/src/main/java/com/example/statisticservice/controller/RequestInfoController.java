package com.example.statisticservice.controller;

import com.example.statisticservice.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestInfoController {

    private final KafkaProducerService kafkaProducerService;

    public RequestInfoController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/api/v1/request-info")
    public ResponseEntity<String> getRequestInfo() {
        // Get request type and execution time
        // Call KafkaProducerService to send the information to Kafka
        // Return an appropriate response
    }
}
