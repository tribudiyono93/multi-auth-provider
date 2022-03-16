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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Component
public class AzureAuthProvider extends OauthProvider {

    @Value("${oauth.provider.azure.audience}")
    private String audience;

    @Value("${oauth.provider.azure.jwks-uri}")
    private String jwkUri;

    @Value("${oauth.provider.azure.issuer}")
    private String issuer;

    @Override
    public String getAudience() {
        return audience;
    }

    @Override
    public String getJWKUri() {
        return jwkUri;
    }

    @Override
    public String getIssuer() {
        return issuer;
    }


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

        return new UserDTO(claim.get("email").asString(), claim.get("name").asString(), "BASIC");
    }
}
