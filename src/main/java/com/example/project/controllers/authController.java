package com.example.project.controllers;


import com.example.project.config.JwtService;
import com.example.project.models.OrginalRoles;
import com.example.project.models.OrginalUsers;
import com.example.project.services.authenticationService;
import com.example.project.user.role.RoleRepository;
import com.example.project.user.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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



    @PostMapping("/signUp")

    public Authentication register1(@RequestBody RegisterRequest request) {
        OrginalUsers user = OrginalUsers.builder()
                .userId(request.getUserID())
                .username(request.getUserName())
                .EMAIL(request.getEmail())
                .Password(passwordEncoder.encode(request.getPassword()))
                .build();
        Set<Optional<OrginalRoles>> roles = new HashSet<>();
        for (String roleName : request.getRole()) {
            Optional<OrginalRoles> role = orgRoleRepository.findByRoleId(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        repository.save(user);
        return service.addUserRole(request.getUserID(), request.getRole());


    }


    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody loginRequest request) {


        service.login(request);
        return ResponseEntity.ok(service.login(request));
    }

}
