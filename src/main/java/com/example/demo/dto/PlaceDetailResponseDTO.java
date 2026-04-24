package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlaceDetailResponseDTO {
    private int placeId;
    private String placeName;
    private String address;
    private String description;
    private String phone;
    private List<ReviewResponseDTO> reviews;
    private Double longitude;
    private double latitude;
}