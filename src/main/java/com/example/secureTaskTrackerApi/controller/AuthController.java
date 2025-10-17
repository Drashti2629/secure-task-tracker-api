package com.example.secureTaskTrackerApi.controller;

import com.example.secureTaskTrackerApi.dto.JwtResponse;
import com.example.secureTaskTrackerApi.dto.LoginRequest;
import com.example.secureTaskTrackerApi.dto.RegisterRequest;
import com.example.secureTaskTrackerApi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        authService.register(request);
        return  ResponseEntity.ok("User Registeres SuccessFully");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
