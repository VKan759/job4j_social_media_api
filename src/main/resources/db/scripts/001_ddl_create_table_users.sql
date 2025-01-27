CREATE TABLE users
(
    id         serial PRIMARY KEY,
    username   text NOT NULL UNIQUE,
    email      text NOT NULL UNIQUE,
    password   text NOT NULL,
    created_at TIMESTAMP
)