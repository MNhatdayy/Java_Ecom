package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.model.AuthenticationResponse;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ){
       return  ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
            ){
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
