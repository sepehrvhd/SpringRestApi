package com.example.project.services;

import com.example.project.config.JwtService;
import com.example.project.controllers.AuthenticationResponse;
import com.example.project.controllers.RegisterRequest;
import com.example.project.controllers.loginRequest;
import com.example.project.models.OrginalRoles;
import com.example.project.models.OrginalUsers;
import com.example.project.user.role.RoleRepository;
import com.example.project.user.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class authenticationService {



    private final userRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

    private final RoleRepository orgRoleRepository;


    public ResponseEntity<String> createUserWithRole(@RequestBody RegisterRequest createUserRequest) {
       return null;
    }

    public AuthenticationResponse login(loginRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserID(),
                        request.getPassword()));

        var user=repository.findByUserId(request.getUserID()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public void addUserRole(String userID, String role) {
        Set<OrginalUsers> usersSet=null;
        OrginalUsers user=repository.findByUserId(userID).get();
        OrginalRoles rolee=orgRoleRepository.findByRoleId(role).get();

        usersSet=rolee.getUsers();
        usersSet.add(user);
        rolee.setUsers(usersSet);
        orgRoleRepository.save(rolee);

    }
}
