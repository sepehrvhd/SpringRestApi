package com.example.project.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAccessDeniedException(Exception ex) {

        String errorMessage = "You are not authorized to access this resource.";
        return ResponseEntity.ok(errorMessage);
    }
}
