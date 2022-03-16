package com.example.multiauthprovider.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthProviderFactory {

    private final GoogleAuthProvider googleAuthProvider;
    private final AzureAuthProvider azureAuthProvider;
    private final OktaAuthProvider oktaAuthProvider;
    private final InternalAuthProvider internalAuthProvider;

    public OauthProvider select(String issuer) {
        if (!issuer.startsWith("https://")) {
            issuer = "https://" + issuer;
        }

        if (issuer.equals(googleAuthProvider.getIssuer())) {
            return googleAuthProvider;
        }

        if (issuer.equals(azureAuthProvider.getIssuer())) {
            return azureAuthProvider;
        }

        if (issuer.equals(oktaAuthProvider.getIssuer())) {
            return oktaAuthProvider;
        }

        if (issuer.equals(internalAuthProvider.getIssuer())) {
            return internalAuthProvider;
        }

        throw new IllegalArgumentException("Invalid Issuer");
    }
}
