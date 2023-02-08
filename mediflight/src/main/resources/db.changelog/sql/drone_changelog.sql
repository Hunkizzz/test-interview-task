CREATE TYPE drone_model AS ENUM
    ('Lightweight', 'Middleweight', 'Cruiserweight', 'Heavyweight');

CREATE TYPE drone_state AS ENUM
    ('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING');

CREATE TABLE drone
(
    id               UUID PRIMARY KEY,
    serial_number    VARCHAR(100) NOT NULL UNIQUE,
    model            drone_model,
    weight_limit     BIGINT,
    battery_capacity INT,
    state            drone_state
);