package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PlaceRepository placeRepository;
    @Autowired private ImageService imageService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(
            @RequestParam Integer placeID,
            @RequestParam Integer rating,
            @RequestParam String comment,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // ปรับให้เช็คแบบไม่สนตัวพิมพ์เล็กใหญ่
            if (!user.getRole().equalsIgnoreCase("USER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("เฉพาะ USER เท่านั้นที่สามารถสร้างรีวิวได้ (Role ปัจจุบัน: " + user.getRole() + ")");
            }

            // ... โค้ดส่วนที่เหลือเหมือนเดิม ...
            Place place = placeRepository.findById(placeID).get();
            Review review = new Review();
            review.setPlace(place);
            review.setUser(user);
            review.setRating(rating);
            review.setComment(comment);
            review.setReviewDate(LocalDate.now());

            Review savedReview = reviewRepository.save(review);
            if (file != null && !file.isEmpty()) {
                imageService.uploadImage(placeID, savedReview, file);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}