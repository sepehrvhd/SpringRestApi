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

import java.util.HashSet;
import java.util.NoSuchElementException;
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


    public OrginalUsers addUserRole(String userID, Set<String> role) {
        OrginalUsers user = repository.findByUserId(userID)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        for (String i:role){

            OrginalRoles rolee = orgRoleRepository.findByRoleId(i)
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));

            // Get the set of roles for the user
            Set<OrginalRoles> userRoles = user.getRoles();

            // If the userRoles set is null, initialize it as a new HashSet
            if (userRoles == null) {
                userRoles = new HashSet<>();
            }

            // Check if the user already has the role
            boolean roleExists = userRoles.stream().anyMatch(r -> r.getRoleId().equals(role));
            if (!roleExists) {
                // Add the role to the set of user roles
                userRoles.add(rolee);

                // Set the updated set of roles back to the user
                user.setRoles(userRoles);

                // Save the user entity to update the database

            } else {
                // Role already exists for the user, no need to add it again

            }
        }

        return repository.save(user);
    }
}
