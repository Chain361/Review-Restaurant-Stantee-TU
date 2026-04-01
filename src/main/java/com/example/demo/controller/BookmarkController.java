package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserBookmarkDTO;
import com.example.demo.service.PlaceService;

@RestController
public class BookmarkController {

    private final PlaceService placeService;

    public BookmarkController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<?> getUserBookmarks(Authentication authentication) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "ยังไม่ได้เข้าสู่ระบบ"));
            }

            String username = authentication.getName();
            List<UserBookmarkDTO> bookmarks = placeService.getUserBookmarks(username);

            return ResponseEntity.ok(bookmarks);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "ดึงรายการบุ๊กมาร์กไม่สำเร็จ"));
        }
    }
}