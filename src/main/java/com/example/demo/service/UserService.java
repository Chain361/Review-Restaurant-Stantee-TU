package com.example.demo.service;

import com.example.demo.entity.User;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RegisterRequest;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String register(RegisterRequest request, boolean isAdmin) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            return "ชื่อผู้ใช้นี้มีอยู่แล้ว";
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(isAdmin ? "ADMIN" : "USER");

        userRepository.save(newUser);

        return "ลงทะเบียนสำเร็จ";
    }

    public boolean authenticate(String username, String rawPassword) {
        User user = findByUsername(username);
        return user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }
}