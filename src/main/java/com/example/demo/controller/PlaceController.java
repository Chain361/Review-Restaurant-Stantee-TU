package com.example.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.service.PlaceService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/places") 
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{placeID}")
    public Place getPlaceByIDPlace(@PathVariable int placeID){
        return placeService.getPlaceById(placeID);
    }
    @GetMapping("/search")
    public ResponseEntity<?> findPlaceWithSearch(@RequestParam("search") String search) {
        List<PlaceDTO> results = placeService.searchPlace(search);

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "ไม่พบสถานที่ตามคำค้นหา"));
        }
        return ResponseEntity.ok(results);
    }
}
