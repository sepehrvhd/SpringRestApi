package com.example.project.controllers;


import com.example.project.DTO.FileUploadRequest;
import com.example.project.Requests.CreateRecordRequest;
import com.example.project.config.JwtService;
import com.example.project.models.Document;
import com.example.project.models.Folder;
import com.example.project.models.NodeBase;
import com.example.project.models.PropertyGroup;
import com.example.project.repositories.NodeDocumentRepository;
import com.example.project.repositories.NodeFolderRepository;
import com.example.project.repositories.nodeBaseRepository;
import com.example.project.repositories.nodePropertyRepository;
import com.example.project.services.UserService;
import com.example.project.services.createRecordService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("app/setting")
@RequiredArgsConstructor

public class RecordController {

    private final nodeBaseRepository nodeBaseRepository;

    private final NodeFolderRepository nodeFolderRepository;

    private final nodePropertyRepository nodePropertyRepository;

    private final NodeDocumentRepository nodeDocumentRepository;

    private final JwtService service;

    private final UserService uservice;


    private final createRecordService createRecordService;

    private final authController authController;

    private final AuthenticationManager manager;



    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: ");
    }


    @PostMapping("/create-folder")

     public ResponseEntity<String> createFolder(@RequestBody CreateRecordRequest request, @RequestParam String path, @RequestHeader("Authorization") String authorizationHeader) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        String jwtToken = extractJwtToken(authorizationHeader);
        String userId = service.exractUsername(jwtToken);

            String folderName = generateRandomFolderName();

            String folderPath = path + "\\" + folderName;
            File folder = new File(folderPath);
            boolean folderCreated = folder.mkdirs();
            if (folderCreated) {
                NodeBase nodebase = new NodeBase();
                nodebase.setNbsUuid(UUID.randomUUID().toString());
                nodebase.setNbsAuthor(userId);
                nodebase.setNbsCreated(LocalDateTime.now());
                nodebase.setNbsName(folderName);
                nodebase.setNbsPath(folderPath);
                nodebase.setFlag("F");

                Folder NodeFolder = new Folder();
                NodeFolder.setUuid(nodebase.getNbsUuid());


                nodeBaseRepository.save(nodebase);
                nodeFolderRepository.save(NodeFolder);

                Map<String, Object> additionalProperties = request.getAdditionalProperties();


                for (Map.Entry<String, Object> entry : additionalProperties.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    PropertyGroup pg = new PropertyGroup();
                    pg.setNpgGroup(request.getMetaData());
                    pg.setNodeBase(nodebase);
                    pg.setNpgName(request.getMetaData() + "." + key);
                    pg.setNpgValue((String) value);
                    nodePropertyRepository.save(pg);
                }

            } else {
                return ResponseEntity.ok("the record doesn't created successfully");
            }

        return ResponseEntity.ok("the record created successfully");
    }


    private String extractJwtToken(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
            return parts[1];
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
    }

    private String generateRandomFolderName() {
        int length = 10;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String characters = "012";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return "kpd" + sb.toString();
    }


    @PostMapping("/upload-file")
    public String Upload(@RequestPart("file") MultipartFile file,
                               @RequestParam("uuid") String folderUuid,
                               @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String jwtToken = extractJwtToken(authorizationHeader);
        String userId = service.exractUsername(jwtToken);


        NodeBase folder=createRecordService.loadFolderByUuid(folderUuid);

        NodeBase nodeBase = new NodeBase();
        nodeBase.setNbsUuid(UUID.randomUUID().toString());
        nodeBase.setNbsAuthor(userId);
        nodeBase.setNbsCreated(LocalDateTime.now());
        nodeBase.setNbsName(file.getOriginalFilename());
        nodeBase.setNbsPath(folder.getNbsPath()+'\\'+nodeBase.getNbsName());

        nodeBaseRepository.save(nodeBase);

        Document document=new Document();
        document.setUuid(nodeBase.getNbsUuid());
        document.setDoc_type(file.getContentType());

        nodeDocumentRepository.save(document);

        Path path= Paths.get(folder.getNbsPath());

        Path targetfile=path.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(),targetfile);

        return ("The file uploaded");
    }
}