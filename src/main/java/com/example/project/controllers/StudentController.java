package com.example.project.controllers;

import com.example.project.models.OrginalStudents;
import com.example.project.repositories.orginalStudentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;

@RestController
public class StudentController {
    private orginalStudentsRepo orginalStudentsRepo;

    public StudentController(orginalStudentsRepo orginalStudentsRepo) {
        this.orginalStudentsRepo = orginalStudentsRepo;
    }

    @GetMapping(path = "app/list-of-users")
    public Iterable<OrginalStudents> list() {

        return orginalStudentsRepo.findAll();
    }

    @PostMapping(path = "app/add-user")
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