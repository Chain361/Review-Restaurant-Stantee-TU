package com.example.demo.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ReviewImages")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewImageID;

    private String fileName;
    private String filePath;
    
    @ManyToOne
    @JoinColumn(name="placeID")
    private Place place;
    
    @ManyToOne
    @JoinColumn(name = "reviewID")
    private Review review;
    
    public Integer getReviewImageID() { return reviewImageID; }
    public void setReviewImageID(Integer reviewImageID) { this.reviewImageID = reviewImageID; } 
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }
    
    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

}