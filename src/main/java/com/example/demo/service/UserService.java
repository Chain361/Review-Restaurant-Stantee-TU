package com.example.demo.service;

import com.example.demo.entity.ImageProfilePath;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.entity.User;
import com.example.demo.repository.ImageProfilePathRepository;
import com.example.demo.repository.ReviewImageRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.dto.UserProfileResponseDTO;
import com.example.demo.dto.UserReviewResponseDTO;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageProfilePathRepository imageProfilePathRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,ImageProfilePathRepository imageProfilePathRepository,ReviewRepository reviewRepository,ReviewImageRepository reviewImageRepository) {
        this.userRepository = userRepository;
        this.imageProfilePathRepository = imageProfilePathRepository;
        this.reviewRepository = reviewRepository;
        this.reviewImageRepository = reviewImageRepository;
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
    public List<UserReviewResponseDTO> getUserReviews(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ไม่พบข้อมูลผู้ใช้"));

        List<Review> reviews = reviewRepository.findByUserUserID(user.getUserID());
        List<UserReviewResponseDTO> responseList = new ArrayList<>();

        for (Review review : reviews) {
            UserReviewResponseDTO dto = new UserReviewResponseDTO();
            dto.setReviewID(review.getReviewID());
            dto.setComment(review.getComment());
            dto.setRating(review.getRating());
            dto.setReviewDate(review.getReviewDate());
            dto.setPlaceName(review.getPlace().getPlaceName());

            List<ReviewImage> images = reviewImageRepository.findByReview_ReviewID(review.getReviewID());
            List<String> imagePaths = new ArrayList<>();

            for (ReviewImage image : images) {
                imagePaths.add(image.getFilePath());
            }

            dto.setReviewImages(imagePaths);
            responseList.add(dto);
        }

        return responseList;
    }
}