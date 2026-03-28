package com.example.demo.dto;

public class PlaceDTO {
    private int placeId;
    private String placeName;
    private String address;
    private String description;
    private String phone;

    public PlaceDTO() {} 
    public PlaceDTO(int placeId, String placeName, String address, String description, String phone) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.address = address;
        this.description = description;
        this.phone = phone;
    }

    public int getPlaceId() {
        return placeId;
    }  
    public String getPlaceName() {
        return placeName;
    }
    public String getAddress() {
        return address;
    }   
    public String getDescription() {
        return description;
    }
    public String getPhone() {
        return phone;
    }
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName; 
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDescription(String description) {
        this.description = description;
    }   
}
