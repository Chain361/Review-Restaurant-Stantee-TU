package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Integer reviewID;
    private String comment;
    private Integer rating;
    private LocalDate reviewDate;
    private String username;
    private String placeName;
    private Integer placeID;
}
