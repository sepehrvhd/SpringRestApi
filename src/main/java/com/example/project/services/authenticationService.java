package com.example.project.services;

import com.example.project.config.JwtService;
import com.example.project.controllers.AuthenticationResponse;
import com.example.project.controllers.RegisterRequest;
import com.example.project.controllers.loginRequest;
import com.example.project.models.OrginalUsers;
import com.example.project.user.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class authenticationService {

    private final userRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user= OrginalUsers.builder()
                .userid(request.getUserID())
                .username(request.getUserName())
                .EMAIL(request.getEmail())
                .Password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);

        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(loginRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserID(),
                        request.getPassword()));

        var user=repository.findByuserid(request.getUserID()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
