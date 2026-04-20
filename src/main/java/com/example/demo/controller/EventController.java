package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingEvents() {
        List<Event> events = eventService.getUpcomingEvents();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Upcoming events retrieved successfully.");
        response.put("data", Map.of("events", events));
        
        return ResponseEntity.ok(response);
    }
}