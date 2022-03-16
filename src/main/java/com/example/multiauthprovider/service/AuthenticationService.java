package com.example.multiauthprovider.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.multiauthprovider.exception.AuthenticationException;
import com.example.multiauthprovider.model.JWTResponse;
import com.example.multiauthprovider.request.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Value("${oauth.provider.internal.client-secret}")
    private String clientSecret;

    @Value("${oauth.provider.internal.issuer}")
    private String issuer;

    @Value("${oauth.provider.internal.audience}")
    private String audience;

    public JWTResponse login(LoginRequest requestBody) throws AuthenticationException {
        //silahkan implementasi proper logic untuk login disini
        if (!requestBody.getEmail().equals("dummy@dummy.com") || !requestBody.getPassword().equals("password")) {
            throw new AuthenticationException("Invalid email or password");
        }
        Date expiresAt = Date.from(new Date().toInstant().plus(1, ChronoUnit.DAYS));

        Algorithm algorithm = Algorithm.HMAC256(clientSecret);
        String token = JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(requestBody.getEmail())
                .withClaim("email", requestBody.getEmail())
                .withClaim("name", "John Doe")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt) // expire date 1 hari
                .sign(algorithm);

        return new JWTResponse(token, expiresAt);
    }
}
