package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.BookmarkRequest;
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
    @GetMapping("/bookmarks/check/{placeId}")
    public ResponseEntity<?> checkBookmarkStatus(@PathVariable int placeId, Authentication authentication) {
        try {
            String username = authentication.getName();
            boolean isFavorite = placeService.checkBookmarkStatus(username, placeId);

            return ResponseEntity.ok(Map.of("isFavorite", isFavorite));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "ไม่สามารถตรวจสอบสถานะบุ๊กมาร์กได้"));
        }
    }
    @PostMapping("/bookmarks")
    public ResponseEntity<?> toggleBookmark(@RequestBody BookmarkRequest request, Authentication authentication) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "กรุณาเข้าสู่ระบบ"));
            }

            String username = authentication.getName();
            Map<String, Object> result = placeService.toggleBookmark(username, request.getPlaceID());

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "ไม่สามารถเปลี่ยนสถานะบุ๊กมาร์กได้"));
        }
    }
}