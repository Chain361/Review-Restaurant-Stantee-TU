package com.example.demo.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "\"Reviews\"")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"reviewID\"")
    private Integer reviewID;

    @Column(name = "\"reviewDate\"")
    private LocalDate reviewDate;

    @Column(name = "\"rating\"")
    private Float rating;

    @Column(name = "\"comment\"")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "\"userID\"")
    private User user;

    @ManyToOne
    @JoinColumn(name = "\"placeID\"")
    private Place place;

    public Review(){}

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}