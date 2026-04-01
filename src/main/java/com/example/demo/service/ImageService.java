package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Place;
import com.example.demo.entity.PlaceImage;
import com.example.demo.entity.Review;
import com.example.demo.entity.ReviewImage;
import com.example.demo.repository.PlaceImageRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.ReviewImageRepository;

import jakarta.transaction.Transactional;

@Service
public class ImageService {

    @Autowired
    private PlaceImageRepository placeImageRepository;
    
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    
    @Autowired
    private PlaceRepository placeRepository;

    private final String BASE_PATH = "uploads/places/";

    @Transactional
    public ReviewImage uploadImage(Integer placeID, Review review, MultipartFile file, String type) throws IOException {
       Place place = placeRepository.findById(placeID)
               .orElseThrow(() -> new RuntimeException("Place not found"));

       String subFolder = type.equalsIgnoreCase("review") ? "reviews/" : "images/";
       Path uploadDir = Paths.get(BASE_PATH + placeID + "/" + subFolder);

       if (!Files.exists(uploadDir)) {
           Files.createDirectories(uploadDir);
       }

       String extension = "";
       String originalFileName = file.getOriginalFilename();
       if (originalFileName != null && originalFileName.contains(".")) {
           extension = originalFileName.substring(originalFileName.lastIndexOf("."));
       }

       // สร้าง Record รูปภาพ
       ReviewImage revImg = new ReviewImage();
       revImg.setPlace(place);
       revImg.setReview(review); // เชื่อมโยงกับ Review ที่เพิ่งสร้าง
       revImg = reviewImageRepository.save(revImg);

       // ตั้งชื่อไฟล์โดยใช้ ID จาก DB
       String fileName = "place_" + placeID + "_rev_" + revImg.getReviewImageID() + extension;
       Path filePath = uploadDir.resolve(fileName);
       Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

       revImg.setFileName(fileName);
       revImg.setFilePath(filePath.toString().replace("\\", "/"));

       return reviewImageRepository.save(revImg);
}   

    private ReviewImage saveReviewImage(Place place, MultipartFile file, Path uploadDir, String extension) throws IOException {
        ReviewImage revImg = new ReviewImage();
        revImg.setPlace(place);
        revImg = reviewImageRepository.save(revImg);
        
        String fileName = "place_" + place.getPlaceID() + "_rev_" + revImg.getReviewImageID() + extension;
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        revImg.setFileName(fileName);
        revImg.setFilePath(filePath.toString().replace("\\", "/"));
        return reviewImageRepository.save(revImg);
    }

    private PlaceImage savePlaceImage(Place place, MultipartFile file, Path uploadDir, String extension) throws IOException {
        PlaceImage placeImg = new PlaceImage();
        placeImg.setPlace(place);
        placeImg = placeImageRepository.save(placeImg);

        String fileName = "place_" + place.getPlaceID() + "_img_" + placeImg.getPlaceImageId() + extension;
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        placeImg.setFileName(fileName);
        placeImg.setFilePath(filePath.toString().replace("\\", "/"));
        return placeImageRepository.save(placeImg);
    }
}