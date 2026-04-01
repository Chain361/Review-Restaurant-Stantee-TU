package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.TopPlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewImageRepository reviewImageRepository;

    public PlaceService(PlaceRepository placeRepository, ReviewImageRepository reviewImageRepository) {
        this.placeRepository = placeRepository;
        this.reviewImageRepository = reviewImageRepository;
    }
    public List<PlaceDTO> searchPlace(String search){
        if(search == null || search.isBlank()) return List.of();
        return placeRepository.searchByKeyword(search)
                            .stream()
                            .map(p -> new PlaceDTO(p.getPlaceID(), p.getPlaceName(), p.getAddress(), p.getDescription(), p.getPhone()))
                            .toList();
    }
    public Place getPlaceById(int id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place with ID " + id + " not found"));
    }
    public List<TopPlaceDTO> getTop5Places() {
        List<Object[]> results = placeRepository.findTopPlaces(PageRequest.of(0, 5));
        List<TopPlaceDTO> topPlaces = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            Object[] row = results.get(i);
            Place place = (Place) row[0];
            Double avgRating = (Double) row[1];
            Long reviewCount = (Long) row[2];

            // แก้ไข: ค้นหารูปภาพโดยใช้ Place ID ของร้านนั้นๆ 
            // ตรวจสอบว่าใน ReviewImageRepository ของคุณมี Method นี้หรือไม่ 
            // (หรือใช้ findByPlace_PlaceID ถ้าใน Entity ReviewImage มี Place โดยตรง)
            List<String> imageUrls = reviewImageRepository.findByPlace_PlaceID(place.getPlaceID())
                    .stream()
                    .map(img -> "http://localhost:8080/images/" + img.getFilePath())
                    .limit(3)
                    .collect(Collectors.toList());

            topPlaces.add(new TopPlaceDTO(
                i + 1,
                place.getPlaceID(),
                place.getPlaceName(),
                avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0, // กันกรณีไม่มี rating
                reviewCount,
                imageUrls
            ));
        }
        return topPlaces;
    }
}