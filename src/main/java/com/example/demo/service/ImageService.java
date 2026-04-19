package com.example.demo.service;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final Path rootLocation = Paths.get("uploads/places");

    @Transactional(rollbackFor = Exception.class)
    public ReviewImage uploadImage(Integer placeID, Review review, MultipartFile file) throws IOException {
        
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("ไม่พบไฟล์ที่อัปโหลด");
        }

        Place place = placeRepository.findById(placeID)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานที่ ID: " + placeID));

        // 1. จัดการ Directory: uploads/places/{placeID}/reviews/
        Path uploadDir = this.rootLocation.resolve(placeID.toString()).resolve("reviews");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 2. บันทึก Entity ก่อนเพื่อให้ได้ ID มาตั้งชื่อไฟล์
        ReviewImage image = new ReviewImage();
        image.setPlace(place);
        image.setReview(review);
        image = reviewImageRepository.save(image);

        // 3. สร้างชื่อไฟล์ใหม่: place_1_review_10.jpg
        String originalFileName = file.getOriginalFilename();
        String extension = (originalFileName != null && originalFileName.contains(".")) 
                           ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
        
        String newFileName = String.format("place_%d_review_%d%s", placeID, image.getReviewImageID(), extension);
        Path destinationFile = uploadDir.resolve(newFileName);

        // 4. บันทึกไฟล์ลง Disk
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        // 5. บันทึก Relative Path หรือแค่ชื่อไฟล์ลง DB (ในที่นี้เก็บ path ย่อยเพื่อความง่าย)
        // ผลลัพธ์จะเป็น: "1/reviews/place_1_review_10.jpg"
        String dbPath = placeID + "/reviews/" + newFileName;
        image.setFileName(originalFileName);
        image.setFilePath(dbPath);

        return reviewImageRepository.save(image);
    }
}