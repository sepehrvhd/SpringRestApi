package com.example.project.controllers;


import com.example.project.config.JwtService;
import com.example.project.models.OrginalRoles;
import com.example.project.models.OrginalUsers;
import com.example.project.user.role.RoleRepository;
import com.example.project.user.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.services.authenticationService;

import java.util.Optional;

@RestController
@RequestMapping(path = "app/auth")
@RequiredArgsConstructor

public class authController {

    private final authenticationService service;


    private final userRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

    private final RoleRepository orgRoleRepository;




    /*@PostMapping("/signUp")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody RegisterRequest request){

    }*/
    @PostMapping("/signUp1")
    public String register1(@RequestBody RegisterRequest request){
        OrginalUsers user = OrginalUsers.builder()
                .userId(request.getUserID())
                .username(request.getUserName())
                .EMAIL(request.getEmail())
                .Password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);

        Optional<OrginalRoles> roleOptional = orgRoleRepository.findByRoleId(request.getRole());
        if (!roleOptional.isPresent()) {
            OrginalRoles newRole = OrginalRoles.builder()
                    .roleId(request.getRole())
                    // Set other properties if needed
                    .build();
            orgRoleRepository.save(newRole);
        }
        var jwtToken=jwtService.generateToken(user);
        AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return jwtToken;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody loginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

}
