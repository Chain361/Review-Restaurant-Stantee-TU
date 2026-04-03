package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "place_images")
@Data
public class PlaceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "placeid")
    private Place place;

    private String fileName;
    private String filePath;

    public PlaceImage() {}
    
    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }
}
