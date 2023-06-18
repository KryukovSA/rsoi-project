package com.example.statisticservice.controller;

//import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.example.statisticservice.service.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class RequestInfoController {
    private final KafkaConsumer kafkaConsumer;

    @Autowired
    public RequestInfoController(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    @GetMapping
    public String getStatistics() {
        // Ваша логика для получения статистики, например, из базы данных или других источников
        // Здесь вы можете вернуть сохраненные статистические данные в формате JSON
        return "Your statistics";
    }
}
