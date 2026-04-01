package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Place;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ImageService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PlaceRepository placeRepository; // เพิ่มเพื่อหา Place
    // @Autowired private UserRepository userRepository; // ถ้ามี User ให้ฉีดเข้ามาด้วย

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> createReview(
        @RequestParam Integer placeID,
        @RequestParam Integer rating,
        @RequestParam String comment,
        @RequestParam(value = "image", required = false) MultipartFile file) {

    try {

        Place place = placeRepository.findById(placeID)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        Review review = new Review();
        review.setPlace(place);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDate.now());

        Review savedReview = reviewRepository.save(review);

        if (file != null && !file.isEmpty()) {
            imageService.uploadImage(placeID, savedReview, file, "review");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
}