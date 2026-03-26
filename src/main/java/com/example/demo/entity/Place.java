package com.example.demo.entity;

public class Place {
    private int placeID;
    private String placeName;
    private String description;
    private String phone;
    private String address;
    private String timePeriod;
    private double latitude;
    private double longitude;
    
    public Place(int placeID,String placeName,String description,String phone,String address,String timePeriod,double latitude,double longitude){
        this.placeID = placeID;
        this.placeName = placeName;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.timePeriod = timePeriod;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public int getPlaceID(){ 
        return placeID; 
    }
    public String getPlaceName() { 
        return placeName; 
    }
    public String getDescription() { 
        return description; 
    }
    public String getPhone() { 
        return phone; 
    }
    public String getAddress() { 
        return address;
    }
    public String getTimePeriod() { 
        return timePeriod; 
    }
    public double getLatitude() { 
        return latitude; 
    }
    public double getLongitude() { 
        return longitude; 
    }
}
