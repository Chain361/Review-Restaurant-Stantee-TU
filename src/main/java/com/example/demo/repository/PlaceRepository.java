package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Integer>{
   Optional<Place> findById(Integer placeID);
    @Query("SELECT p FROM Place p WHERE " +
           "LOWER(p.placeName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Place> searchByKeyword(@Param("keyword") String keyword);
} 
