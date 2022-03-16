package com.example.multiauthprovider.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleException(AuthenticationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
