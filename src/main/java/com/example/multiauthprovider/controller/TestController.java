package com.example.multiauthprovider.controller;

import com.example.multiauthprovider.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello from multi auth provider");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin-resource")
    public ResponseEntity<String> adminResource() {
        return ResponseEntity.ok("Hello from role based resource");
    }

    @PreAuthorize("hasAnyAuthority('NON-ADMIN')")
    @GetMapping("/non-admin-resource")
    public ResponseEntity<String> basicResource(Authentication authentication) {
        UserDTO principal = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok("Hello " + principal.getName());
    }
}
