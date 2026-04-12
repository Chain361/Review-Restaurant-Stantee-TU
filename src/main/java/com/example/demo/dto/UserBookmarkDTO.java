package com.example.demo.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBookmarkDTO {

    @JsonProperty("PlaceID")
    private Integer placeId;

    @JsonProperty("PlaceName")
    private String placeName;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("FilePath")
    private String filePath;

    @JsonProperty("AddDate")
    private LocalDateTime addDate;

    public UserBookmarkDTO() {
    }

    public UserBookmarkDTO(Integer placeId, String placeName, String category, String filePath, LocalDateTime addDate) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.category = category;
        this.filePath = filePath;
        this.addDate = addDate;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
