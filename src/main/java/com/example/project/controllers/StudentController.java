package com.example.project.controllers;

import com.example.project.models.OrginalStudents;
import com.example.project.repositories.orginalStudentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.project.services.AuthorizationService;

import java.util.Map;

@RestController
public class StudentController {
    private orginalStudentsRepo orginalStudentsRepo;

    @Autowired
    private AuthorizationService authorizationService;

    public StudentController(orginalStudentsRepo orginalStudentsRepo) {
        this.orginalStudentsRepo = orginalStudentsRepo;
    }

    @GetMapping(path = "app/list-of-users")
    public Authentication list() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;

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
}