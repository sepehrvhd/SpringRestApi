package com.example.project.services;

import com.example.project.models.OrginalUsers;
import com.example.project.user.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database
        Optional<OrginalUsers> user = userRepository.findByUserId(username);
        // Convert user entity to UserDetails
        return User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .authorities(user.get().getAuthorities())
                .build();
    }


}
