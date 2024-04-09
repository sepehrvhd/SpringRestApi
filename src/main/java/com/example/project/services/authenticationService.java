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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

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
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserID(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<OrginalUsers> user = repository.findByUserId(request.getUserID());




        Collection<String> userAuthorities =user.get().getAuthorityNames();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String authority : userAuthorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);





        user= Optional.of(repository.findByUserId(request.getUserID()).orElseThrow());
        var jwtToken=jwtService.generateToken(user.get());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user.get()).build();
    }


    public Authentication addUserRole(String userID, Set<String> role) {
        OrginalUsers user = repository.findByUserId(userID)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        for (String i:role){

            OrginalRoles rolee = orgRoleRepository.findByRoleId(i)
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));


            Set<OrginalRoles> userRoles = user.getRoles();


            if (userRoles == null) {
                userRoles = new HashSet<>();
            }


            boolean roleExists = userRoles.stream().anyMatch(r -> r.getRoleId().equals(role));
            if (!roleExists) {

                userRoles.add(rolee);


                user.setRoles(userRoles);



            } else {


            }
        }
        repository.save(user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<String> userAuthorities =user.getAuthorityNames();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String authority : userAuthorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authorities
        );
         SecurityContextHolder.getContext().setAuthentication(newAuthentication);
         return SecurityContextHolder.getContext().getAuthentication();

    }
}
