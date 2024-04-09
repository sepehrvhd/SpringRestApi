package com.example.project.controllers;


import com.example.project.models.OrginalUsers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {


    private String token;

    private OrginalUsers user;

}
