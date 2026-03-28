package com.example.demo.entity;

import jakarta.persistence.Table;

@Table(name = "PlaceCategory")
public class PlaceCategory {
    private int CategoryID;
    private String CategoryName;


    public int getCategoryID() {
        return this.CategoryID;
    }
    public String getCategoryName(){
        return this.CategoryName;
    }
    public void setCategoryID(int categoryID) {
        this.CategoryID = categoryID;
    }
    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }
}
