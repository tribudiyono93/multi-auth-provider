package com.example.multiauthprovider.controller;

import com.example.multiauthprovider.exception.AuthenticationException;
import com.example.multiauthprovider.model.JWTResponse;
import com.example.multiauthprovider.request.LoginRequest;
import com.example.multiauthprovider.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest requestBody) throws AuthenticationException {
        return ResponseEntity.ok(authenticationService.login(requestBody));
    }
}
