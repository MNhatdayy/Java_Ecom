package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.ForgotDTO;
import com.HutechB6.Ecommerce.DTO.TokenRequest;
import com.HutechB6.Ecommerce.model.AuthenticationResponse;
import com.HutechB6.Ecommerce.model.Role;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ){
        request.setRole(Role.USER);
        return  ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ){
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken().trim();
        User currentUser = authenticationService.getCurrentUser(token);
        return ResponseEntity.ok(currentUser);
    }
//    @PostMapping("check-otp")
//    public ResponseEntity<ForgotDTO> checkOTP(int otp) {
//
//    }


}
