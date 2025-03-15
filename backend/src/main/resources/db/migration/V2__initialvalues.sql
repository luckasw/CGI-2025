INSERT INTO
    planes (model, rows, seats_per_row)
VALUES
    ('Boeing 737', 40, 6);

INSERT INTO
    planes (model, rows, seats_per_row)
VALUES
    ('Airbus A320', 30, 6);

INSERT INTO
    flights (
        departure_time,
        arrival_time,
        origin,
        destination,
        price,
        plane_id
    )
VALUES
    (
        '2025-04-14 16:15:11',
        '2025-04-14 17:15:11',
        'TLL',
        'RIX',
        150.00,
        1
    );

INSERT INTO
    flights (
        departure_time,
        arrival_time,
        origin,
        destination,
        price,
        plane_id
    )
VALUES
    (
        '2025-04-14 18:15:11',
        '2025-04-14 22:15:11',
        'RIX',
        'FRA',
        200.00,
        2
    );

INSERT INTO
    flights (
        departure_time,
        arrival_time,
        origin,
        destination,
        price,
        plane_id
    )
VALUES
    (
        '2025-04-14 13:15:11',
        '2025-04-14 17:15:11',
        'TLL',
        'HEL',
        80.00,
        1
    );
