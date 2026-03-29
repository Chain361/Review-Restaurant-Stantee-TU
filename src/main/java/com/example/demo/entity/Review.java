package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.GenerationType;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;
    private LocalDate reviewDate;
    private double rating;
    private String comment;
    private int userID;
    private int placeID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
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
