package com.example.demo.service;

import com.example.demo.dto.PlaceReviewsResponseDTO;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.data.domain.Page; // เพิ่ม import นี้
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service 
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private PlaceRepository placeRepository;
    
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

    public PlaceReviewsResponseDTO getAllPlaceReviews(Integer placeID) {
        if (!placeRepository.existsById(placeID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ไม่พบสถานที่");
        }

        List<Review> reviews = reviewRepository.findByPlacePlaceID(placeID);

        List<PlaceReviewsResponseDTO.ReviewItem> reviewItems = new ArrayList<>();

        for (Review review : reviews) {
            PlaceReviewsResponseDTO.ReviewItem item = new PlaceReviewsResponseDTO.ReviewItem();
            item.setReviewID(review.getReviewID());
            item.setRating(review.getRating());
            item.setReviewDate(review.getReviewDate());
            item.setComment(review.getComment());

            if (review.getUser() != null) {
                item.setUserID(review.getUser().getUserID());
                item.setUsername(review.getUser().getUsername());
                item.setFirstName(review.getUser().getFirstName());
                item.setLastName(review.getUser().getLastName());
            }

            List<ReviewImage> images = reviewImageRepository.findByReview_ReviewID(review.getReviewID());
            List<PlaceReviewsResponseDTO.ReviewImageItem> imageItems = new ArrayList<>();

            for (ReviewImage image : images) {
                PlaceReviewsResponseDTO.ReviewImageItem imageItem = new PlaceReviewsResponseDTO.ReviewImageItem();
                imageItem.setFilePath(image.getFilePath());
                imageItems.add(imageItem);
            }

            item.setReviewImages(imageItems);
            reviewItems.add(item);
        }

        PlaceReviewsResponseDTO.ReviewsData data = new PlaceReviewsResponseDTO.ReviewsData();
        data.setReviews(reviewItems);

        PlaceReviewsResponseDTO response = new PlaceReviewsResponseDTO();
        response.setStatus("success");
        response.setMessage("Reviews for placeID = " + placeID + " retrieved successfully.");
        response.setData(data);

        return response;
    }
}