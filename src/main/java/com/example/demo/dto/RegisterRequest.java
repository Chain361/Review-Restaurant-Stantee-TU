package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("PasswordHash")
    private String passwordHash;

    @JsonProperty("Firstname")
    private String Firstname;
    
    @JsonProperty("Lastname")
    private String Lastname;
    

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
    public String getPassword(){
        return this.password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
}
