package com.example.project.services.Rest;

import com.example.project.models.OrginalStudents;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class RestClient {

    private final RestTemplate restTemplate;
    private final String  authentication;

    public RestClient(String authentication) {
        this.restTemplate = new RestTemplate();
        this.authentication = authentication;
    }

    public String extractJwtToken(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getPassword();
        }
        return null;
    }


    public ResponseEntity<String> getListOfUsers(String url) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authentication);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);


        return responseEntity;

    }

    public ResponseEntity<String> KeydocLogin(String url) throws URISyntaxException {
        RequestEntity<Void> requestEntity = new RequestEntity<>( HttpMethod.GET, new URI(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return responseEntity;
    }

}
