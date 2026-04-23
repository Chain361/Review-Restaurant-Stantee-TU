package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaceReviewsResponseDTO {
    private String status;
    private String message;
    private ReviewsData data;

    @Data
    public static class ReviewsData {
        private List<ReviewItem> reviews;
    }

    @Data
    public static class ReviewItem {
        private Integer reviewID;
        private Integer rating;
        private LocalDate reviewDate;
        private String comment;
        private Integer userID;
        private String username;
        private String firstName;
        private String lastName;
        private List<ReviewImageItem> reviewImages;
    }

    @Data
    public static class ReviewImageItem {
        @JsonProperty("file_path")
        private String filePath;
    }
}