CREATE TABLE medication
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    weight   FLOAT        NOT NULL CHECK (weight < 500),
    code     VARCHAR(255) NOT NULL UNIQUE,
    image    BLOB,
    drone_id UUID
);