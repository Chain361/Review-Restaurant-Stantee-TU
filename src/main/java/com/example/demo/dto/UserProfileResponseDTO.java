package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileResponseDTO {

    private String username;
    private String firstName;
    private String lastName;

    @JsonProperty("file_path")
    private String filePath;

    public UserProfileResponseDTO() {
    }

    public UserProfileResponseDTO(String username, String firstName, String lastName, String filePath) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.filePath = filePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
