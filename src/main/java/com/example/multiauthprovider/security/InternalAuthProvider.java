package com.example.multiauthprovider.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.multiauthprovider.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InternalAuthProvider extends OauthProvider {

    @Value("${oauth.provider.internal.client-secret}")
    private String clientSecret;

    @Value("${oauth.provider.internal.issuer}")
    private String issuer;

    @Value("${oauth.provider.internal.audience}")
    private String audience;

    @Override
    public String getAudience() {
        return audience;
    }

    @Override
    public String getJWKUri() {
        return null;
    }

    @Override
    public String getIssuer() {
        return issuer;
    }

    @Override
    public UserDTO verify(DecodedJWT decodedJWT) {
        Algorithm algorithm = Algorithm.HMAC256(clientSecret);

        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withAudience(getAudience())
                .acceptExpiresAt(1)
                .build();

        jwtVerifier.verify(decodedJWT);

        Map<String, Claim> claim = decodedJWT.getClaims();

        return new UserDTO(claim.get("email").asString(), claim.get("name").asString(), "NON-ADMIN");
    }
}


