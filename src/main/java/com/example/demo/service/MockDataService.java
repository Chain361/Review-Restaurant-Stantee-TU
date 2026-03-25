package com.example.demo.service;

import com.example.demo.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockDataService {

    // --- MOCK DATA LISTS ---

    private final List<User> mockUsers = Arrays.asList(
        new User(1, "johndoe", "pass123", "John", "Doe"),
        new User(2, "janedoe", "pass456", "Jane", "Doe"),
        new User(3, "somsak_bkk", "pass789", "Somsak", "Bkk")
    );

    private final List<Place> mockPlaces = Arrays.asList(
        new Place(101, "Somtum Restaurant", "Authentic Thai papaya salad", "021234567", "Sukhumvit Rd, Bangkok", "10:00 - 22:00", 13.7367, 100.5231),
        new Place(102, "Central Park", "A large public park in NY", "+12123106600", "New York, NY", "06:00 - 01:00", 40.7851, -73.9683),
        new Place(103, "Coffee House", "Cozy coffee shop", "0812345678", "Siam Square, Bangkok", "08:00 - 20:00", 13.7445, 100.5298)
    );

    private final List<Review> mockReviews = Arrays.asList(
        new Review(1001, LocalDate.of(2023, 10, 26), 4.5, "Delicious Somtum!", 1, 101), // User 1 reviews Place 101
        new Review(1002, LocalDate.of(2023, 10, 27), 5.0, "Great place to relax", 2, 102),  // User 2 reviews Place 102
        new Review(1003, LocalDate.of(2023, 10, 28), 3.0, "Good coffee but noisy", 1, 103)  // User 1 reviews Place 103
    );

    private final List<RestaurantMenu> mockMenus = Arrays.asList(
        new RestaurantMenu(1, "Papaya Salad", 80.0),
        new RestaurantMenu(2, "Grilled Chicken", 150.0),
        new RestaurantMenu(3, "Sticky Rice", 20.0),
        new RestaurantMenu(4, "Americano", 90.0),
        new RestaurantMenu(5, "Latte", 100.0)
    );

    private final List<RestaurantHasMenu> mockPlaceHasMenus = Arrays.asList(
        // Place 101 has menus 1, 2, 3
        new RestaurantHasMenu(1, 101),
        new RestaurantHasMenu(2, 101),
        new RestaurantHasMenu(3, 101),
        // Place 103 has menus 4, 5
        new RestaurantHasMenu(4, 103),
        new RestaurantHasMenu(5, 103)
    );

    public List<User> getAllUsers() {
        return mockUsers;
    }

    // 2. ดึง Place ตาม ID
    public Place getPlaceById(int placeId) {
        return mockPlaces.stream()
                .filter(p -> p.getPlaceID() == placeId)
                .findFirst()
                .orElse(null);
    }

    public List<Review> getReviewsByPlaceId(int placeId) {
        return mockReviews.stream()
                .filter(r -> r.getPlaceID() == placeId)
                .collect(Collectors.toList());
    }
    public List<RestaurantMenu> getMenusByPlaceId(int placeId) {
        List<Integer> menuIds = mockPlaceHasMenus.stream()
                .filter(phm -> phm.getPlaceID() == placeId)
                .map(RestaurantHasMenu::getMenuID)
                .collect(Collectors.toList());

        return mockMenus.stream()
                .filter(m -> menuIds.contains(m.getMenuID()))
                .collect(Collectors.toList());
    }
}