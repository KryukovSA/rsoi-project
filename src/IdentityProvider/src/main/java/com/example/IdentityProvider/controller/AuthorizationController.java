package com.example.IdentityProvider.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;
import static com.example.IdentityProvider.IdentityProviderApplication.ISSUER;
import static com.example.IdentityProvider.IdentityProviderApplication.HMAC_SECRET;

@RestController
public class AuthorizationController {

    @GetMapping("/authorize")
    public String handleAuthorization(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("scope") String scope
    ) throws JOSEException {

        // Check if the requested scopes are supported
        if (!scope.contains("openid") || !scope.contains("profile") || !scope.contains("email")) {
            return "Invalid scope requested";
        }

        // Assume the user is authenticated and retrieve the user information
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

        // Redirect the user back to the client with the ID token
        String idToken = signedJWT.serialize();
        return "redirect:" + redirectUri + "?id_token=" + idToken;
    }
}

