package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class UserReviewResponseDTO {
    private Integer reviewID;
    private String placeName;
    private Integer rating;
    private String comment;
    private LocalDate reviewDate;
    private List<String> reviewImages;
}