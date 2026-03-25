package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Place;
import com.example.demo.service.MockDataService;

@RestController
@RequestMapping("/places") 
public class PlaceController {

    private final MockDataService mockDataService;

    public PlaceController(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    @GetMapping("/{id}")
    public Place getPlaceByIDPlace(@PathVariable int id){
        return mockDataService.getPlaceById(id);
    }
}
