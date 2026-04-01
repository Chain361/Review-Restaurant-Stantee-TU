package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PlaceCategory")
public class PlaceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int CategoryID;

    @Column(name = "CategoryName")
    private String CategoryName;

    @Column(name = "PlaceID")
    private int PlaceID;

    public PlaceCategory() {
    }

    public int getCategoryID() {
        return this.CategoryID;
    }

    public String getCategoryName() {
        return this.CategoryName;
    }

    public int getPlaceID() {
        return this.PlaceID;
    }

    public void setCategoryID(int categoryID) {
        this.CategoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }

    public void setPlaceID(int placeID) {
        this.PlaceID = placeID;
    }
}