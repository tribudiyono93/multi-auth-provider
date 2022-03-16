package com.example.multiauthprovider.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.multiauthprovider.model.UserDTO;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public abstract class OauthProvider {

    public abstract String getAudience();
    public abstract String getJWKUri();
    public abstract String getIssuer();

    public UserDTO verify(DecodedJWT decodedJWT) throws MalformedURLException, JwkException {
        JwkProvider provider =  new JwkProviderBuilder(new URL(getJWKUri())).build();
        Jwk jwk = provider.get(decodedJWT.getKeyId());
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withAudience(getAudience())
                .acceptExpiresAt(1)
                .build();

        jwtVerifier.verify(decodedJWT);

        Map<String, Claim> claim = decodedJWT.getClaims();

        return new UserDTO(claim.get("email").asString(), claim.get("name").asString(), "NON-ADMIN");
    }
}
