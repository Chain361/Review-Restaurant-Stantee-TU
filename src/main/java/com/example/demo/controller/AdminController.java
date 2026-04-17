package com.example.demo.controller;

import com.example.demo.service.JwtService;
import com.example.demo.service.PlaceService;
import com.example.demo.service.ReviewService;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.entity.Place;
import com.example.demo.entity.PlaceImage;
import com.example.demo.entity.Review;
import com.example.demo.repository.PlaceImageRepository;
import com.example.demo.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final JwtService jwtService;
    private final PlaceService placeService;

    private final PlaceImageRepository placeImageRepository; 

    public AdminController(JwtService jwtService, PlaceService placeService, PlaceImageRepository placeImageRepository,ReviewService reviewService,ReviewRepository reviewRepository) {
        this.jwtService = jwtService;
        this.placeService = placeService;
        this.placeImageRepository = placeImageRepository;
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository; 
    }

    private boolean isAdmin(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String pureToken = token.substring(7);
                String role = jwtService.extractRole(pureToken);
                return "ADMIN".equals(role);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    // Parts place
    @PostMapping(value = "/places/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPlace(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestPart("placeName") String placeName,
            @RequestPart("description") String description,
            @RequestPart("phone") String phone,
            @RequestPart("address") String address,
            @RequestPart("timePeriod") String timePeriod,
            @RequestPart("latitude") String latitude,
            @RequestPart("longitude") String longitude,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admin only"));
        }

        Place place = new Place();
        place.setPlaceName(placeName);
        place.setDescription(description);
        place.setPhone(phone);
        place.setAddress(address);
        place.setTimePeriod(timePeriod);
        place.setLatitude(Double.parseDouble(latitude));
        place.setLongitude(Double.parseDouble(longitude));

        Place savedPlace = placeService.createPlace(place, images);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                "placeId", savedPlace.getPlaceID()
        ));
    }
    @PutMapping(value = "/places/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editPlace(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestPart(value = "placeName", required = false) String placeName,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "phone", required = false) String phone,
            @RequestPart(value = "address", required = false) String address,
            @RequestPart(value = "timePeriod", required = false) String timePeriod,
            @RequestPart(value = "latitude", required = false) String latitude,
            @RequestPart(value = "longitude", required = false) String longitude,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Admin only"));
        }

        // สร้าง Object เพื่อเก็บเฉพาะค่าที่ส่งมา
        Place updateDetails = new Place();
        updateDetails.setPlaceName(placeName);
        updateDetails.setDescription(description);
        updateDetails.setPhone(phone);
        updateDetails.setAddress(address);
        updateDetails.setTimePeriod(timePeriod);
        
        if (latitude != null) updateDetails.setLatitude(Double.parseDouble(latitude));
        if (longitude != null) updateDetails.setLongitude(Double.parseDouble(longitude));

        Place updatedPlace = placeService.updatePlace(id, updateDetails, images);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Updated successfully"
        ));
    }
    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviews(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admin only"));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("reviewDate").descending());
        Page<ReviewResponseDTO> reviews = reviewService.findAll(pageable);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/reviews/{reviewID}")
    public ResponseEntity<?> deleteReview(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Integer reviewID) {

        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admin only"));
        }
        try {
            reviewService.deleteReview(reviewID);
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Review ID " + reviewID + " deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}