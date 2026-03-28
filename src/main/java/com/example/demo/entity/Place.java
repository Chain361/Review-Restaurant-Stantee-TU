package com.example.demo.entity;

import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Table(name="places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeID;
    private String placeName;
    private String description;
    private String phone;
    private String address;
    private String timePeriod;
    private double latitude;
    private double longitude;
    
    @OneToMany(mappedBy = "place")
    private List<Review> reviews;
    
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
