package com.example.demo.controller;

import com.example.demo.service.ReviewService;
import com.example.demo.dto.ReviewResponseDTO;
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
    
    @Autowired private ReviewService reviewService; 

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(
            @RequestParam Integer placeID,
            @RequestParam Float rating,
            @RequestParam String comment,
            @RequestParam(value = "image", required = false) MultipartFile file,
            Authentication authentication) {
        try {
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลผู้ใช้งานในระบบ"));

            Place place = placeRepository.findById(placeID)
                    .orElseThrow(() -> new RuntimeException("ไม่พบสถานที่ที่ต้องการรีวิว"));

            Review review = new Review();
            review.setUser(user);   
            review.setPlace(place); 
            review.setRating(rating);
            review.setComment(comment);
            review.setReviewDate(LocalDate.now());

            Review savedReview = reviewRepository.save(review);
            
            
            if (file != null && !file.isEmpty()) {
                imageService.uploadImage(placeID, savedReview, file);
            }

            ReviewResponseDTO responseDTO = reviewService.convertToDTO(savedReview);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("เกิดข้อผิดพลาดภายในระบบ");
        }
    }
}