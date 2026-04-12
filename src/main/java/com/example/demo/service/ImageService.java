package com.example.demo.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

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

    // แนะนำให้ใช้ Path object แทน String
    private final Path rootLocation = Paths.get("uploads/places");

    @Transactional(rollbackFor = Exception.class) // ถ้า Copy ไฟล์พลาด ให้ Rollback DB
    public ReviewImage uploadImage(Integer placeID, Review review, MultipartFile file) throws IOException {
        
        // 1. Validation เบื้องต้น
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("ไม่พบไฟล์ที่อัปโหลด");
        }

        // เช็ค Content Type (เลือกเฉพาะภาพ)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("อนุญาตให้อัปโหลดเฉพาะไฟล์รูปภาพเท่านั้น");
        }

        Place place = placeRepository.findById(placeID)
                .orElseThrow(() -> new RuntimeException("ไม่พบสถานที่ ID: " + placeID));

        // 2. จัดการ Directory: uploads/places/{placeID}/reviews/
        Path uploadDir = this.rootLocation.resolve(placeID.toString()).resolve("reviews");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 3. บันทึกข้อมูลลง DB รอบแรกเพื่อให้ได้ ID
        ReviewImage image = new ReviewImage();
        image.setPlace(place);
        image.setReview(review);
        image = reviewImageRepository.save(image);

        // 4. สร้างชื่อไฟล์ใหม่ (ป้องกันอักขระพิเศษจากชื่อไฟล์เดิม)
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        
        // ชื่อไฟล์รูปแบบ: place_1_review_10.png
        String fileName = String.format("place_%d_review_%d%s", placeID, image.getReviewImageID(), extension);
        Path filePath = uploadDir.resolve(fileName);

        // 5. บันทึกไฟล์ลง Disk
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("เกิดข้อผิดพลาดในการเขียนไฟล์ลงเซิร์ฟเวอร์: " + e.getMessage());
        }

        // 6. อัปเดตข้อมูลกลับลง Entity
        image.setFileName(fileName);
        // เก็บ path สำหรับเรียกใช้ (แนะนำให้เก็บแบบสัมพัทธ์เพื่อให้ย้ายเครื่องได้ง่าย)
        image.setFilePath(filePath.toString().replace("\\", "/"));

        return reviewImageRepository.save(image);
    }
}