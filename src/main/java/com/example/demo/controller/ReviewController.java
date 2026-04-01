package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.entity.Place;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ImageService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PlaceRepository placeRepository;

    // สร้างรีวิว (USER)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(
            @RequestParam Integer placeID,
            @RequestParam Integer rating,
            @RequestParam String comment,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().equals("USER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("เฉพาะ USER เท่านั้นที่สามารถสร้างรีวิวได้");
            }

            if (rating < 1 || rating > 5) {
                return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
            }

            Place place = placeRepository.findById(placeID)
                    .orElseThrow(() -> new RuntimeException("Place not found"));

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

    // ลบรีวิว (ADMIN)
    @DeleteMapping("/delete/{reviewID}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewID) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("เฉพาะ ADMIN เท่านั้นที่สามารถลบรีวิวได้");
            }

            Review review = reviewRepository.findById(reviewID)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            reviewRepository.delete(review);

            return ResponseEntity.ok("ลบรีวิวสำเร็จ");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}