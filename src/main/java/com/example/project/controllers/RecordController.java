package com.example.project.controllers;

import com.example.project.config.JwtService;
import com.example.project.models.Folder;
import com.example.project.models.NodeBase;
import com.example.project.repositories.NodeFolderRepository;
import com.example.project.repositories.nodeBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("app/setting")
@RequiredArgsConstructor

public class RecordController {

    private final nodeBaseRepository nodeBaseRepository;

    private final NodeFolderRepository nodeFolderRepository;

    private final JwtService service;

    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestParam String path,@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken=extractJwtToken(authorizationHeader);
        String UserId = service.exractUsername(jwtToken);

        String folderName=generateRandomFolderName();

        String folderPath = path + "\\" + folderName;
        File folder = new File(folderPath);
        boolean folderCreated = folder.mkdirs();
        if(folderCreated){
            NodeBase nodebase=new NodeBase();
            nodebase.setNbsUuid(UUID.randomUUID().toString());
            nodebase.setNbsAuthor(UserId);
            nodebase.setNbsCreated(LocalDateTime.now());
            nodebase.setNbsName(folderName);
            nodebase.setNbsPath(folderPath);
            nodebase.setFlag("F");


            Folder NodeFolder=new Folder();
            NodeFolder.setUuid(nodebase.getNbsUuid());

            nodeBaseRepository.save(nodebase);

            nodeFolderRepository.save(NodeFolder);

            return ResponseEntity.ok("Folder created successfully");
            }
        else
            {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create folder");
            }

        }




    private String extractJwtToken(String authorizationHeader) {
        // Assuming the Authorization header value is in the format "Bearer <token>"
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
            return parts[1];
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
    }

    private String generateRandomFolderName() {
        int length = 10; // Length of the random string
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String characters = "012";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return "kpd" + sb.toString();
    }

}