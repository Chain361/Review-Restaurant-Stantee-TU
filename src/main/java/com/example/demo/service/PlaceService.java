package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.UserBookmarkDTO;
import com.example.demo.entity.Place;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import java.sql.Timestamp;
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    
    public PlaceService(PlaceRepository placeRepository,BookmarkRepository bookmarkRepository,UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
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
}