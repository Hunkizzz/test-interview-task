CREATE TABLE DRONE_BATTERY_AUDIT
(
    id           UUID   NOT NULL,
    timestamp    DATETIME NOT NULL,
    battery_level INT      NOT NULL,
    drone_id     UUID   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (drone_id) REFERENCES Drone (id)
);