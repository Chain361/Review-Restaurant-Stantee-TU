package com.example.demo.dto;


import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TopPlaceDTO {
    private int rank;
    private int id;
    private String name;
    private Double rating;
    private Long reviewCount;
    private List<String> images;
}
