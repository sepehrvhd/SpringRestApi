package com.example.project.controllers;

import com.example.project.models.OrginalStudents;
import com.example.project.repositories.orginalStudentsRepo;
import com.example.project.services.Rest.RestClient;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.project.services.AuthorizationService;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
public class StudentController {
    private final orginalStudentsRepo orginalStudentsRepo;


    public StudentController(orginalStudentsRepo orginalStudentsRepo) {
        this.orginalStudentsRepo = orginalStudentsRepo;
    }

    @GetMapping (path = "app/list-of-users")
    public Iterable<OrginalStudents> list() {

        return orginalStudentsRepo.findAll();

    }
    @GetMapping (path = "app/list-of-users1")
    public ResponseEntity<String> list1(HttpServletRequest request)throws URISyntaxException {
        String authorizationHeader = request.getHeader("Authorization");
        RestClient restClient = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            String Token = jwtToken;
            restClient = new RestClient(Token);
        } else {

        }
        
        return restClient.getListOfUsers("http://localhost:8181/app/list-of-users");

    }



    @PostMapping(path = "app/add-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public OrginalStudents createStudent(@RequestBody final OrginalStudents orginalStudents) {

        orginalStudentsRepo.save(orginalStudents);
        return orginalStudents;

    }
    
    @PostMapping(path = "app/get-user")
    public OrginalStudents getStudent(@RequestParam final long id) {
        return orginalStudentsRepo.findById(id).get();
    }

    @PostMapping(path = "app/delete-user")
    public String deleteStudent(@RequestParam final long id) {

        orginalStudentsRepo.deleteById(id);
        return "the item with id of " + id + " deleted";
    }

    @PostMapping(path = "app/update-user")
    public String updateUser(@RequestParam long id, @RequestBody Map<String, Object> updatedFields) {

        OrginalStudents existingOrginalStudents = orginalStudentsRepo.findById(id).get();



        ModelMapper modelMapper = new ModelMapper();
        OrginalStudents updatedOrginalStudents = modelMapper.map(updatedFields, OrginalStudents.class);
        existingOrginalStudents.updateFields(updatedOrginalStudents);
        orginalStudentsRepo.save(existingOrginalStudents);

        return ("User updated successfully");
    }

    public String getStudents() {
        RestTemplate restTemplate = new RestTemplate();
        String source = "http://localhost:8181/app/list-of-users";

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(source, String.class);

            return response.toString();
        }
        else {
            return "false";
        }
    }
}