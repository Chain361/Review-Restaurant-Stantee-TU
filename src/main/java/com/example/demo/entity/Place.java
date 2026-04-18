package com.example.demo.entity;

import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @Column(name = "\"placeID\"")
    private int placeID;

    @Column(name = "\"placeName\"")
    private String placeName;

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"phone\"")
    private String phone;

    @Column(name = "\"address\"")
    private String address;

    @Column(name = "\"timePeriod\"")
    private String timePeriod;

    @Column(name = "\"latitude\"")
    private double latitude;

    @Column(name = "\"longitude\"")
    private double longitude;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceImage> placeImages;
    public Place() {}

    
    public Place(int placeID, String placeName, String description, String phone, String address, String timePeriod, double latitude, double longitude){
        this.placeID = placeID;
        this.placeName = placeName;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.timePeriod = timePeriod;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // getters
    public int getPlaceID(){ return placeID; }
    public String getPlaceName() { return placeName; }
    public String getDescription() { return description; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getTimePeriod() { return timePeriod; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public List<PlaceImage> getPlaceImages() { return placeImages; }
    public List<Review> getReviews() { return reviews; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }
    public void setDescription(String description) { this.description = description; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setTimePeriod(String timePeriod) { this.timePeriod = timePeriod; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}