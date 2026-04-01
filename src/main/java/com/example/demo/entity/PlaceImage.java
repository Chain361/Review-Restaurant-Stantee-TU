package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PlaceImages")
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaceImageID")
    private int PlaceImageID;

    @Column(name = "FileName")
    private String FileName;

    @Column(name = "FilePath")
    private String FilePath;

    @Column(name = "PlaceID")
    private int PlaceID;

    public PlaceImage() {
    }

    public int getPlaceImageID() {
        return this.PlaceImageID;
    }

    public void setPlaceImageID(int placeImageID) {
        this.PlaceImageID = placeImageID;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String fileName) {
        this.FileName = fileName;
    }

    public String getFilePath() {
        return this.FilePath;
    }

    public void setFilePath(String filePath) {
        this.FilePath = filePath;
    }

    public int getPlaceID() {
        return this.PlaceID;
    }

    public void setPlaceID(int placeID) {
        this.PlaceID = placeID;
    }
}
