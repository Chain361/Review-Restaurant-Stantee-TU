package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToggleBookmarkRequest {

    @JsonProperty("PlaceID")
    @JsonAlias({"placeId", "placeID"})
    private int placeId;

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
