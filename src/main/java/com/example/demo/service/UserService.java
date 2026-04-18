package com.example.demo.service;

import com.example.demo.entity.ImageProfilePath;
import com.example.demo.entity.User;
import com.example.demo.repository.ImageProfilePathRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserProfileResponseDTO;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageProfilePathRepository imageProfilePathRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, ImageProfilePathRepository imageProfilePathRepository) {
        this.userRepository = userRepository;
        this.imageProfilePathRepository = imageProfilePathRepository;
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
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setRole(isAdmin ? "ADMIN" : "USER");

        userRepository.save(newUser);

        return "ลงทะเบียนสำเร็จ";
    }

    public boolean authenticate(String username, String rawPassword) {
        User user = findByUsername(username);
        return user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    public UserProfileResponseDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ไม่พบข้อมูลผู้ใช้"));

        Optional<ImageProfilePath> profileImage = imageProfilePathRepository.findByUserID(user.getUserID());

        String filePath = profileImage.map(ImageProfilePath::getFilePath).orElse("");

        return new UserProfileResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                filePath
        );
    }
}