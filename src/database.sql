CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cards (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage DECIMAL(6, 2) NOT NULL,
    element_type VARCHAR(255)  NOT NULL,
    card_type VARCHAR(255)  NOT NULL,
    package_id   INT,
    user_name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS packages (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);