package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopController {
    @GetMapping("/top")
    public ResponseEntity<?> getTopFivePlaces() {
            
        return ResponseEntity.ok("Welcome to the Travel Review App!");
    }
}
