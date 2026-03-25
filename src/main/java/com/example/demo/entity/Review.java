package com.example.demo.entity;

import java.time.LocalDate;

public class Review {
    private int reviewID;
    private LocalDate reviewDate;
    private double rating;
    private String comment;
    private int userID;
    private int placeID;
    
    public Review(int reviewID, LocalDate reviewDate, double rating, String comment, int userID, int placeID) {
        this.reviewID = reviewID;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.comment = comment;
        this.userID = userID;
        this.placeID = placeID;
    }
    public int getReviewID() { return reviewID; }
    public LocalDate getReviewDate() { return reviewDate; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public int getUserID() { return userID; }
    public int getPlaceID() { return placeID; }
}
