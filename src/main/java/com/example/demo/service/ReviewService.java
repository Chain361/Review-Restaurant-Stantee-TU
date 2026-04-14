package com.example.demo.service;

import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    public ReviewResponseDTO convertToDTO(Review review) {
        if (review == null) return null;

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewID(review.getReviewID());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setReviewDate(review.getReviewDate());
    
        // ดึงข้อมูลจาก Entity มาใส่ DTO
        if (review.getUser() != null) {
            dto.setUsername(review.getUser().getUsername());
        }
        
        if (review.getPlace() != null) {
            dto.setPlaceID(review.getPlace().getPlaceID());
            dto.setPlaceName(review.getPlace().getPlaceName());
        }
        
        return dto;
    }
    public Review getReviewByPlaceID(Integer placeID) {
        if(reviewRepository.findByPlacePlaceID(placeID).isEmpty()) return null;
        return reviewRepository.findByPlacePlaceID(placeID).get(0);
    }
}