package com.example.IdentityProvider.kafkaex;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessagingService {
    @Value("send-user-event")
    private String sendUserTopic;

    private final KafkaTemplate<String , Object> kafkaTemplate;
    public void sendUser(UserSentEvent userSentEvent) {
        kafkaTemplate.send(sendUserTopic, userSentEvent.getUsername(), userSentEvent);
    }

}
