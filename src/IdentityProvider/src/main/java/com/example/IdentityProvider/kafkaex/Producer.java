package com.example.IdentityProvider.kafkaex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaMessagingService kafkaMessagingService;
    private final ModelMapper modelMapper;
    public UserSentEvent sendUserEvent(UserSentEvent userSentEvent) {
        kafkaMessagingService.sendUser(modelMapper.map(userSentEvent, UserSentEvent.class));
        return userSentEvent;
    }
}
