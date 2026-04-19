package com.example.demo.repository;
import  java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PlaceImage;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Integer> {
    List<PlaceImage> findByPlace_PlaceID(int placeID);
}