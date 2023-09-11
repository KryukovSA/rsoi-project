package com.example.IdentityProvider.kafkaex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSentEvent {
    private String username;
    private String role;
}
