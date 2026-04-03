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

CREATE TABLE "Users" (
    "userID" SERIAL PRIMARY KEY,
    "username" VARCHAR(255),
    "passwordHash" VARCHAR(255),
    "firstName" VARCHAR(255),
    "lastName" VARCHAR(255),
    "role" VARCHAR(255)
);

CREATE TABLE "Reviews" (
    "reviewID" SERIAL PRIMARY KEY,
    "reviewDate" DATE,
    "rating" INTEGER,
    "comment" VARCHAR(255),
    "userID" INTEGER,
    "placeID" INTEGER,
    CONSTRAINT fk_review_user FOREIGN KEY ("userID") REFERENCES "Users"("userID"),
    CONSTRAINT fk_review_place FOREIGN KEY ("placeID") REFERENCES "Places"("placeID")
);

CREATE TABLE places (
    "placeID" SERIAL PRIMARY KEY,
    "placeName" VARCHAR(255),
    "description" VARCHAR(255),
    "phone" VARCHAR(255),
    "address" VARCHAR(255),
    "timePeriod" VARCHAR(255),
    "latitude" DOUBLE PRECISION,
    "longitude" DOUBLE PRECISION
);


-- Sample data
INSERT INTO restaurants (name, address, city, phone, email, website) VALUES
('Pizza Palace', '123 Main St', 'Springfield', '555-0101', 'info@pizzapalace.com', 'www.pizzapalace.com'),
('Burger Haven', '456 Oak Ave', 'Springfield', '555-0102', 'info@burgerhaven.com', 'www.burgerhaven.com'),
('Sushi Master', '789 Elm Rd', 'Springfield', '555-0103', 'info@sushimaster.com', 'www.sushimaster.com');

INSERT INTO "Users" ("username", "passwordHash", "firstName", "lastName", "role") 
VALUES 
    (
        'user', 
        '$2a$12$PvUt4uXbjFaFX8vfQaVVMel3o/Ko2tmEcIViA8EFkTeQnJtO4T6ka', 
        'Regular', 
        'User', 
        'USER'
    ),
    (
        'admin', 
        '$2a$12$PvUt4uXbjFaFX8vfQaVVMel3o/Ko2tmEcIViA8EFkTeQnJtO4T6ka', 
        'System', 
        'Admin', 
        'ADMIN'
);

INSERT INTO places (
    "placeName", 
    "description", 
    "phone", 
    "address", 
    "timePeriod", 
    "latitude", 
    "longitude"
) VALUES 
(
    'The Grand Palace', 
    'Historic royal complex and home to the Temple of the Emerald Buddha.', 
    '+66 2 623 5500', 
    'Na Phra Lan Rd, Phra Borom Maha Ratchawang, Phra Nakhon, Bangkok 10200', 
    '08:30 - 15:30', 
    13.7500, 
    100.4913
),
(
    'Wat Arun', 
    'Iconic riverside temple known as the Temple of Dawn, featuring a towering central prang.', 
    '+66 2 891 2185', 
    '158 Thanon Wang Doem, Wat Arun, Bangkok Yai, Bangkok 10600', 
    '08:00 - 18:00', 
    13.7437, 
    100.4889
),
(
    'Chatuchak Weekend Market', 
    'One of the largest weekend markets in the world with over 15,000 stalls.', 
    '+66 2 272 4813', 
    'Kamphaeng Phet 2 Rd, Chatuchak, Bangkok 10900', 
    '09:00 - 18:00', 
    13.7999, 
    100.5506
),
(
    'Lumphini Park', 
    'A rare open public space, trees, and playgrounds in the Thai capital, featuring boating on an artificial lake.', 
    '+66 2 252 7006', 
    'Rama IV Rd, Lumphini, Pathum Wan, Bangkok 10330', 
    '04:30 - 21:00', 
    13.7314, 
    100.5415
),
(
    'ICONSIAM', 
    'Large, modern riverside mall featuring luxury shopping, an indoor floating market, and dining.', 
    '+66 2 495 7000', 
    '299 Charoen Nakhon Rd, Khlong Ton Sai, Khlong San, Bangkok 10600', 
    '10:00 - 22:00', 
    13.7267, 
    100.5105
);

INSERT INTO "Reviews" (
    "reviewDate", 
    "rating", 
    "comment", 
    "userID", 
    "placeID"
) VALUES 
(
    '2023-10-15', 
    5, 
    'Absolutely stunning architecture! A must-visit in Bangkok.', 
    1, 
    1  -- The Grand Palace
),
(
    '2023-10-16', 
    4, 
    'Beautiful at sunset. The stairs are quite steep though.', 
    1, 
    2  -- Wat Arun
),
(
    '2023-11-05', 
    5, 
    'Great place to find souvenirs and street food, but it gets very crowded.', 
    1, 
    3  -- Chatuchak Weekend Market
),
(
    '2023-11-20', 
    4, 
    'Very peaceful park in the middle of the busy city. Saw some monitor lizards!', 
    2, 
    4  -- Lumphini Park
),
(
    '2023-12-01', 
    5, 
    'Amazing shopping mall with a great indoor floating market on the ground floor.', 
    2, 
    5  -- ICONSIAM
),
(
    '2023-12-10', 
    3, 
    'Incredibly crowded and hot during the day, but an essential historical site.', 
    2, 
    1  -- The Grand Palace
);
