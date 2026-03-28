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
    //private final MockDataService mockDataService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public String register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            return "ชื่อผู้ใช้นี้มีอยู่แล้ว";
        }


        String hashed = passwordEncoder.encode(request.getPasswordHash());
        User newUser = new User();
        newUser.setUsername(request.getUsername()); 

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