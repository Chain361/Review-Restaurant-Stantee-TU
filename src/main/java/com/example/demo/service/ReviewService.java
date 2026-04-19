package com.example.demo.service;

import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.data.domain.Page; // เพิ่ม import นี้
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service 
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    
    public Page<ReviewResponseDTO> findAll(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(review -> this.convertToDTO(review));
    }

    public ReviewResponseDTO convertToDTO(Review review) {
        if (review == null) return null;

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewID(review.getReviewID());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setReviewDate(review.getReviewDate());

        if (review.getUser() != null) {
            dto.setUsername(review.getUser().getUsername());
        }
        
        if (review.getPlace() != null) {
            dto.setPlaceID(review.getPlace().getPlaceID());
            dto.setPlaceName(review.getPlace().getPlaceName());
        }

        if (review.getImages() != null) {
            List<String> imageUrls = review.getImages().stream()
                .map(img -> img.getFilePath())
                .toList();
            dto.setReviewImages(imageUrls);
        }
        
        return dto;
    }

    public void deleteReview(Integer reviewID) {
        if (reviewRepository.existsById(reviewID)) {
            reviewRepository.deleteById(reviewID);
        } else {
            throw new RuntimeException("Review not found with ID: " + reviewID);
        }
    }

    public Review getReviewByPlaceID(Integer placeID) {
        List<Review> list = reviewRepository.findByPlacePlaceID(placeID);
        return list.isEmpty() ? null : list.get(0);
    }
}