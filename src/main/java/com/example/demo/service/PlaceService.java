package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.PlaceRepository;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
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
}