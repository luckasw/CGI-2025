CREATE TABLE planes (
    id SERIAL PRIMARY KEY,
    model VARCHAR(255) NOT NULL UNIQUE,
    rows INT NOT NULL CHECK (rows > 0),
    seats_per_row INT NOT NULL CHECK (seats_per_row > 0)
);

CREATE TABLE flights (
    id SERIAL PRIMARY KEY,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL CHECK (arrival_time > departure_time),
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    plane_id INT NOT NULL,
    FOREIGN KEY (plane_id) REFERENCES planes (id)
);
