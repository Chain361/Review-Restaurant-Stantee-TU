package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import com.example.demo.dto.RegisterRequest;


@RestController
@RequestMapping("/auth") 
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        boolean isAuthenticated = userService.authenticate(
                request.getUsername(),
                request.getPassword()
                
        );

        if (isAuthenticated) {
            User user = userService.findByUsername(request.getUsername());
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "role", user.getRole(),
                    "username", user.getUsername(),
                    "firstName", user.getFirstName()
            ));
        }

        return ResponseEntity.status(401)
                .body(Map.of("message", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // ปกติ register ผ่าน API = USER
        String result = userService.register(request, false);

        if (result.equals("ชื่อผู้ใช้นี้มีอยู่แล้ว")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", result));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", result));
    }
}