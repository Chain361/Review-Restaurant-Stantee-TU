package com.example.demo.entity;

public class User {
    private int userID;
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;

    public User(int userID,String username,String passwordHash,String firstName,String lastName){
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    
    public int getUserID(){
        return userID;
    }
    public String getUsername(){
        return username;
    } 
    public String getPasswordHash(){
        return passwordHash;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
}
