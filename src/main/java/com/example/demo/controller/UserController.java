package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserProfileResponseDTO;
import com.example.demo.dto.UserReviewResponseDTO;
import com.example.demo.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(Authentication authentication) {
        String username = authentication.getName();
        UserProfileResponseDTO response = userService.getUserProfile(username);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/reviews")
    public ResponseEntity<List<UserReviewResponseDTO>> getUserReviews(Authentication authentication) {
        String username = authentication.getName();
        List<UserReviewResponseDTO> response = userService.getUserReviews(username);
        return ResponseEntity.ok(response);
    }
}
