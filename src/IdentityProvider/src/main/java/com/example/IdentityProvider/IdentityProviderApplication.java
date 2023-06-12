package com.example.IdentityProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@SpringBootApplication
public class IdentityProviderApplication {

    public static final String ISSUER = "your-issuer";
    public static final String CLIENT_SECRET = "your-client-secret";
    public static final String HMAC_SECRET = "your-hmac-secret";

    public static void main(String[] args) {
        SpringApplication.run(IdentityProviderApplication.class, args);
    }


}

