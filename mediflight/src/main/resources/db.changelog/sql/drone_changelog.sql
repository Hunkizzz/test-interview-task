CREATE TYPE drone_model AS ENUM
    ('LIGHTWEIGHT', 'MIDDLEWEIGHT', 'CRUISERWEIGHT', 'HEAVYWEIGHT');

CREATE TYPE drone_state AS ENUM
    ('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING');

CREATE TABLE drone
(
    id               UUID PRIMARY KEY,
    serial_number    VARCHAR(100) NOT NULL UNIQUE,
    model            drone_model,
    weight_limit     BIGINT,
    battery_capacity INT CHECK (battery_capacity <= 100),
    state            drone_state
);