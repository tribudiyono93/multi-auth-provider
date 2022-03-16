package com.example.multiauthprovider.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OktaAuthProvider extends OauthProvider {

    @Value("${oauth.provider.okta.audience}")
    private String audience;

    @Value("${oauth.provider.okta.jwks-uri}")
    private String jwkUri;

    @Value("${oauth.provider.okta.issuer}")
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
}
