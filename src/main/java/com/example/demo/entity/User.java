package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userID;
    @Column(name = "username")
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "firstname")
    private String Firstname;
    @Column(name = "lastname")
    private String Lastname;

    public User(){
        
    }
    public User(int userID,String username,String passwordHash,String firstName,String lastName){
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.Firstname=firstName;
        this.Lastname=lastName;
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
        return this.Firstname;
    }
    public String getLastName(){
        return this.Lastname;
    }
    public void setUsername(String username) {
       this.username = username;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    
}
