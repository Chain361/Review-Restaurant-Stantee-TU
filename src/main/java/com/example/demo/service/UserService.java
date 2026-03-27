package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RegisterRequest;
@Service
public class UserService {

    private final MockDataService mockDataService;

    public UserService(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    public User findByUsername(String username) {
        return mockDataService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    public String register(RegisterRequest request) {
        User existingUser = findByUsername(request.getUsername());

        if (existingUser != null) {
            return "ชื่อผู้ใช้นี้มีอยู่แล้ว";
        }

        User newUser = new User(
                0,
                request.getUsername(),
                request.getPasswordHash(),
                "",
                ""
        );

        mockDataService.getAllUsers().add(newUser);

        return "ลงทะเบียนสำเร็จ";
    }
    // ✅ ใช้ plain text ก่อน
    public boolean authenticate(String username, String rawPassword) {
        User user = findByUsername(username);

        if (user != null) {
            return rawPassword.equals(user.getPasswordHash());
        }
        return false;
    }
}