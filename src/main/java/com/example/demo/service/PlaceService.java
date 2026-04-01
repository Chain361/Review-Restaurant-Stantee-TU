package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.UserBookmarkDTO;
import com.example.demo.dto.TopPlaceDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import java.sql.Timestamp;import com.example.demo.repository.ReviewImageRepository;
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ReviewImageRepository reviewImageRepository;

    public PlaceService(PlaceRepository placeRepository,BookmarkRepository bookmarkRepository,UserRepository userRepository, ReviewImageRepository reviewImageRepository) {
        this.placeRepository = placeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
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
    public List<UserBookmarkDTO> getUserBookmarks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้"));

        List<Object[]> rows = bookmarkRepository.findUserBookmarks(user.getUserID());
       
        return rows.stream().map(row -> {
            int placeId = row[0] != null ? ((Number) row[0]).intValue() : 0;
            String placeName = row[1] != null ? row[1].toString() : "";
            String category = row[2] != null ? row[2].toString() : "";
            String filePath = row[3] != null ? row[3].toString() : "";

            LocalDateTime addDate = null;
            if (row[4] instanceof Timestamp timestamp) {
                addDate = timestamp.toLocalDateTime();
            } else if (row[4] instanceof LocalDateTime localDateTime) {
                addDate = localDateTime;
            }

            return new UserBookmarkDTO(placeId, placeName, category, filePath, addDate);
        }).toList();
    }
    public boolean checkBookmarkStatus(String username, int placeId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้"));

        if (placeRepository.findById(placeId).isEmpty()) {
            throw new RuntimeException("ไม่พบสถานที่");
        }

        return bookmarkRepository.existsBookmark(placeId, user.getUserID());
    }
}