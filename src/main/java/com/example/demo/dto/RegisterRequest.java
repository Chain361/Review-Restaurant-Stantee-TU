package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    @JsonProperty("Username")
    private String username;

    @JsonProperty("PasswordHash")
    private String passwordHash;

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
}
