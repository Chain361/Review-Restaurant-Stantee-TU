package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"Bookmark\"")
@IdClass(BookmarkId.class)
public class Bookmark {

    @Id
    @Column(name = "\"UserID\"")
    private Integer userID;

    @Id
    @Column(name = "\"PlaceID\"")
    private Integer placeID;

    @Column(name = "\"AddDate\"")
    private LocalDateTime addDate;

    public Bookmark() {
    }

    public Bookmark(Integer userID, Integer placeID, LocalDateTime addDate) {
        this.userID = userID;
        this.placeID = placeID;
        this.addDate = addDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer placeID) {
        this.placeID = placeID;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}