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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;

    private LocalDate reviewDate;
    private double rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    public Review() {}

    public int getReviewID() { return reviewID; }
    public LocalDate getReviewDate() { return reviewDate; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public User getUser() { return user; }
    public Place getPlace() { return place; }

    public void setReviewID(int reviewID) { this.reviewID = reviewID; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
    public void setRating(double rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setUser(User user) { this.user = user; }
    public void setPlace(Place place) { this.place = place; }
}