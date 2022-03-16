package com.example.multiauthprovider.filter;

import com.auth0.jwk.JwkException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.multiauthprovider.model.UserDTO;
import com.example.multiauthprovider.security.OauthProvider;
import com.example.multiauthprovider.security.OauthProviderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {

    private final OauthProviderFactory oauthProviderFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1].trim();

        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            OauthProvider oauthProvider = oauthProviderFactory.select(decodedJWT.getIssuer());
            UserDTO user = oauthProvider.verify(decodedJWT);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null,
                    user.getAuthorities()
            );

            //set security context, means the user is authenticated to access protected resources
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JWTDecodeException | JwkException de) {
            log.warn("Invalid token", de);
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
