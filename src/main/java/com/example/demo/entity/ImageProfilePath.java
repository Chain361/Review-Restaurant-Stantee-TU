package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"ImageProfile_path\"")
public class ImageProfilePath {

    @Id
    @Column(name = "\"userID\"")
    private Integer userID;

    @Column(name = "\"file_name\"")
    private String fileName;

    @Column(name = "\"file_path\"")
    private String filePath;

    public ImageProfilePath() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
