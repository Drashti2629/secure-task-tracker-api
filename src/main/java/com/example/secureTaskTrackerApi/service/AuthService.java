package com.example.secureTaskTrackerApi.service;

import com.example.secureTaskTrackerApi.dto.JwtResponse;
import com.example.secureTaskTrackerApi.dto.LoginRequest;
import com.example.secureTaskTrackerApi.dto.RegisterRequest;
import com.example.secureTaskTrackerApi.entity.User;
import com.example.secureTaskTrackerApi.repository.UserRepository;
import com.example.secureTaskTrackerApi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
     private final UserRepository userRepo;
     private final PasswordEncoder passwordEncoder;
     private final AuthenticationManager authManager;
     private final JwtUtil jwtUtil;
     private final UserDetailsService userDetailsService;


    public void register(RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepo.save(user);
    }
    public JwtResponse login(LoginRequest request){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
//        UserDetails user = userRepo.findByEmail(request.getEmail()).map(u -> org.springframework.security.core.userdetails.User
//                .withUsername(u.getEmail())
//                .password(u.getPassword())
//                .authorities("USER").build())
//                .orElseThrow();

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtUtil.generateToken(userDetails);
        return  new JwtResponse(token);
    }

}
