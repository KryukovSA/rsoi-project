package com.example.IdentityProvider.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.IdentityProvider.IdentityProviderApplication.*;


@RestController
public class TokenController {

    @PostMapping("/token")
    public Map<String, Object> handleTokenRequest(
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri
    ) throws JOSEException {

        // Verify the client credentials
        if (!clientId.equals("your-client-id") || !clientSecret.equals(CLIENT_SECRET)) {
            throw new InvalidTokenException("Invalid client credentials");
        }

        // Verify the authorization code
        // Here, you would typically validate the code against a database or cache
        // and retrieve the associated user information

        // In this example, we assume the code is valid and retrieve the user information
        String userId = "123456";
        String username = "john_doe";
        String email = "john.doe@example.com";

        // Generate an ID token (JWT)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(ISSUER)
                .subject(userId)
                .claim("username", username)
                .claim("email", email)
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        JWSSigner signer = new MACSigner(HMAC_SECRET.getBytes());
        signedJWT.sign(signer);

        // Create the token response
        Map<String, Object> tokenResponse = new HashMap<>();
        tokenResponse.put("access_token", "your-access-token");
        tokenResponse.put("expires_in", 3600);
        tokenResponse.put("id_token", signedJWT.serialize());

        return tokenResponse;
    }
}
