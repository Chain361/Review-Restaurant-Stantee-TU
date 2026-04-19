package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ImageProfilePath;

@Repository
public interface ImageProfilePathRepository extends JpaRepository<ImageProfilePath, Integer> {
    Optional<ImageProfilePath> findByUserID(Integer userID);
}