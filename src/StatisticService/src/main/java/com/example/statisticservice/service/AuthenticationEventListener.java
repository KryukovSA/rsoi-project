package com.example.statisticservice.service;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    private final KafkaProducer kafkaProducer;

    public AuthenticationEventListener(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            kafkaProducer.sendEvent("Successful login for user: " + username);
        } else if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            String username = (String) event.getAuthentication().getPrincipal();
            kafkaProducer.sendEvent("Failed login attempt for user: " + username);
        }
    }
}
