CREATE TABLE posts
(
    id              serial PRIMARY KEY,
    header          text NOT NULL UNIQUE,
    description     text,
    attachment_file text,
    created_at      TIMESTAMP DEFAULT VALUE CURRENT_TIMESTAMP,
    user_id         INT REFERENCES users (id)
)