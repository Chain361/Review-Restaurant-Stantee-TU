package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"eventID\"")
    private Integer eventID;

    @Column(name = "\"eventName\"")
    private String eventName;

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"eventDate\"")
    private LocalDate eventDate;

    @Column(name = "\"placeID\"")
    private Integer placeID;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Event() {}
}