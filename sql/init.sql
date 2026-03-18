-- Create tables for restaurant review system

-- Restaurants table
CREATE TABLE restaurants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    website VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reviews table
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    restaurant_id INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

-- Sample data
INSERT INTO restaurants (name, address, city, phone, email, website) VALUES
('Pizza Palace', '123 Main St', 'Springfield', '555-0101', 'info@pizzapalace.com', 'www.pizzapalace.com'),
('Burger Haven', '456 Oak Ave', 'Springfield', '555-0102', 'info@burgerhaven.com', 'www.burgerhaven.com'),
('Sushi Master', '789 Elm Rd', 'Springfield', '555-0103', 'info@sushimaster.com', 'www.sushimaster.com');

INSERT INTO reviews (restaurant_id, user_name, rating, comment) VALUES
(1, 'John Doe', 5, 'Excellent pizza! Best in town.'),
(1, 'Jane Smith', 4, 'Good pizza but a bit pricey.'),
(2, 'Bob Johnson', 5, 'Amazing burgers and friendly staff!'),
(3, 'Alice Williams', 4, 'Fresh sushi, great quality.');
