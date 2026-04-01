package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

public class BookmarkId implements Serializable {

    private Integer userID;
    private Integer placeID;

    public BookmarkId() {
    }

    public BookmarkId(Integer userID, Integer placeID) {
        this.userID = userID;
        this.placeID = placeID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookmarkId)) return false;
        BookmarkId that = (BookmarkId) o;
        return Objects.equals(userID, that.userID) &&
               Objects.equals(placeID, that.placeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, placeID);
    }
}
