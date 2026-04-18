package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.PlaceDetailResponseDTO;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.dto.UserBookmarkDTO;
import com.example.demo.dto.TopPlaceDTO;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.BookmarkId;
import com.example.demo.entity.Place;
import com.example.demo.entity.PlaceImage;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.entity.User;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.PlaceImageRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.example.demo.repository.ReviewImageRepository;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewService reviewService;
    private final PlaceImageRepository placeImageRepository;

    public PlaceService(PlaceRepository placeRepository, 
                        BookmarkRepository bookmarkRepository, 
                        UserRepository userRepository, 
                        ReviewImageRepository reviewImageRepository,
                        ReviewService reviewService,
                        PlaceImageRepository placeImageRepository) {
        this.placeRepository = placeRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.reviewService = reviewService;
        this.placeImageRepository = placeImageRepository;
    }


    public Place createPlace(Place place, List<MultipartFile> images) {
        Place savedPlace = placeRepository.save(place);

        if (images != null && !images.isEmpty()) {
            Path uploadDir = Paths.get("uploads/places/" + savedPlace.getPlaceID());
            try {
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                for (MultipartFile file : images) {
                    String originalFileName = file.getOriginalFilename();
                    String extension = (originalFileName != null && originalFileName.contains("."))
                            ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
                    String newFileName = "place_" + savedPlace.getPlaceID() + "_" + System.currentTimeMillis() + extension;

                    Files.copy(file.getInputStream(), uploadDir.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);

                    PlaceImage placeImage = new PlaceImage();
                    placeImage.setPlace(savedPlace);
                    placeImage.setFileName(originalFileName);
                    placeImage.setFilePath(savedPlace.getPlaceID() + "/" + newFileName);
                    placeImageRepository.save(placeImage);
                }
            } catch (IOException e) {
                throw new RuntimeException("อัปโหลดรูปภาพไม่สำเร็จ: " + e.getMessage());
            }
        }

        return savedPlace;
    }
 
    @Transactional
    public Place updatePlace(int id, Place updateDetails, List<MultipartFile> images) {
        Place existingPlace = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));

        if (updateDetails.getPlaceName() != null && !updateDetails.getPlaceName().trim().isEmpty()) {
            existingPlace.setPlaceName(updateDetails.getPlaceName());
        }
        if (updateDetails.getDescription() != null) {
            existingPlace.setDescription(updateDetails.getDescription());
        }
        if (updateDetails.getPhone() != null) {
            existingPlace.setPhone(updateDetails.getPhone());
        }
        if (updateDetails.getAddress() != null) {
            existingPlace.setAddress(updateDetails.getAddress());
        }
        if (updateDetails.getTimePeriod() != null) {
            existingPlace.setTimePeriod(updateDetails.getTimePeriod());
        }
        if (updateDetails.getLatitude() != 0) {
            existingPlace.setLatitude(updateDetails.getLatitude());
        }
        if (updateDetails.getLongitude() != 0) {
            existingPlace.setLongitude(updateDetails.getLongitude());
        }

        if (images != null && !images.isEmpty()) {
            if (existingPlace.getPlaceImages() != null && !existingPlace.getPlaceImages().isEmpty()) {
                for (PlaceImage oldImg : existingPlace.getPlaceImages()) {
                    try {
                        if (oldImg.getFilePath() != null) {
                            Files.deleteIfExists(Paths.get(oldImg.getFilePath()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    placeImageRepository.delete(oldImg);
                }
                existingPlace.getPlaceImages().clear();
            }

            String uploadDir = "uploads/places/";
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    try {
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = Paths.get(uploadDir + fileName);
                        Files.createDirectories(path.getParent());
                        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                        PlaceImage newImg = new PlaceImage();
                        newImg.setFileName(fileName);
                        newImg.setFilePath(path.toString());
                        newImg.setPlace(existingPlace);
                        placeImageRepository.save(newImg);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file", e);
                    }
                }
            }
        }

        return placeRepository.save(existingPlace);
    }
    @Transactional
    public void deletePlace(int id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));

        if (place.getPlaceImages() != null) {
            for (PlaceImage image : place.getPlaceImages()) {
                try {
                    Files.deleteIfExists(Paths.get(image.getFilePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                placeImageRepository.delete(image);
            }
        }

        placeRepository.delete(place);
    }
    private void deleteFileOnServer(String filePath) {
        try {
            Path path = Paths.get("uploads/" + filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("ไม่สามารถลบไฟล์ได้: " + e.getMessage());
        }
    }
    public PlaceDetailResponseDTO getPlaceDetail(int id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place with ID " + id + " not found"));

        PlaceDetailResponseDTO dto = new PlaceDetailResponseDTO();
        dto.setPlaceId(place.getPlaceID());
        dto.setPlaceName(place.getPlaceName());
        dto.setAddress(place.getAddress());
        dto.setDescription(place.getDescription());
        dto.setPhone(place.getPhone());

        List<ReviewResponseDTO> reviewDTOs = place.getReviews().stream().map(review -> {
            ReviewResponseDTO rDto = reviewService.convertToDTO(review);
            
            if (review.getImages() != null) {
                List<String> imageUrls = review.getImages().stream()
                        .map(ReviewImage::getFilePath)
                        .toList();
                rDto.setReviewImages(imageUrls);
            }

            return rDto;
        }).toList();

        dto.setReviews(reviewDTOs);
        return dto;
    }

    public List<PlaceDTO> searchPlace(String search){
        if(search == null || search.isBlank()) return List.of();
        return placeRepository.searchByKeyword(search)
                            .stream()
                            .map(p -> new PlaceDTO(p.getPlaceID(), p.getPlaceName(), p.getAddress(), p.getDescription(), p.getPhone()))
                            .toList();
    }

    public PlaceDetailResponseDTO getPlaceById(int id) {
        return getPlaceDetail(id);
    }
    public List<TopPlaceDTO> getTop5Places() {
            List<Object[]> results = placeRepository.findTopPlaces(PageRequest.of(0, 5));
            List<TopPlaceDTO> topPlaces = new ArrayList<>();

            for (int i = 0; i < results.size(); i++) {
                Object[] row = results.get(i);
                Place place = (Place) row[0];
                Double avgRating = (Double) row[1];
                Long reviewCount = (Long) row[2];

                List<String> imageUrls = placeImageRepository.findByPlace_PlaceID(place.getPlaceID())
                        .stream()
                        .map(img -> {
                            String path = img.getFilePath();
                            if (path != null && path.contains("/")) {
                                return path.substring(path.indexOf("/") + 1);
                            }
                            return path;
                        })
                        .limit(3)
                        .toList();

                topPlaces.add(new TopPlaceDTO(
                    i + 1,
                    place.getPlaceID(),
                    place.getPlaceName(),
                    avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0,
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

    public Map<String, Object> toggleBookmark(String username, int placeId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้"));

        if (placeRepository.findById(placeId).isEmpty()) {
            throw new RuntimeException("ไม่พบสถานที่");
        }

        BookmarkId bookmarkId = new BookmarkId(user.getUserID(), placeId);

        if (bookmarkRepository.existsById(bookmarkId)) {
            bookmarkRepository.deleteById(bookmarkId);

            return Map.of(
                    "status", "unbookmarked",
                    "isFavorite", false
            );
        }

        Bookmark bookmark = new Bookmark(
                user.getUserID(),
                placeId,
                LocalDateTime.now()
        );

        bookmarkRepository.save(bookmark);

        return Map.of(
                "status", "bookmarked",
                "isFavorite", true
        );
    }
}