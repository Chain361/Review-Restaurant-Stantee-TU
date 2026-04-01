package com.example.demo.service;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Place;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;

@Service
public class ImageService {

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private PlaceRepository placeRepository;

    private final String BASE_PATH = "uploads/places/";

    public ReviewImage uploadImage(Integer placeID, Review review, MultipartFile file) throws IOException {

        Place place = placeRepository.findById(placeID)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        Path uploadDir = Paths.get(BASE_PATH + placeID + "/reviews/");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        ReviewImage image = new ReviewImage();
        image.setPlace(place);
        image.setReview(review);

        image = reviewImageRepository.save(image);

        String fileName = "place_" + placeID + "_review_" + image.getReviewImageID() + extension;

        Path filePath = uploadDir.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        image.setFileName(fileName);
        image.setFilePath(filePath.toString().replace("\\", "/"));

        return reviewImageRepository.save(image);
    }
}