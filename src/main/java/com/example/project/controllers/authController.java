package com.example.project.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.services.authenticationService;

@RestController
@RequestMapping(path = "app/auth")
@RequiredArgsConstructor
public class authController {

    private final authenticationService service;


    @PostMapping("/signUp")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody loginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

}
