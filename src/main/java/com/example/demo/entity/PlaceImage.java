package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "PlaceImages")
@Data
public class PlaceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "placeid",referencedColumnName = "\"placeID\"")
    private Place place;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_path")
    private String filePath;

    public PlaceImage() {}
    
    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }
}
