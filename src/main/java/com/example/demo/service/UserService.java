package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

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

    // ✅ ใช้ plain text ก่อน
    public boolean authenticate(String username, String rawPassword) {
        User user = findByUsername(username);

        if (user != null) {
            return rawPassword.equals(user.getPasswordHash());
        }
        return false;
    }
}