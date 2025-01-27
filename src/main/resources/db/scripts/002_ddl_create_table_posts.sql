CREATE TABLE if not exists posts
(
    id              serial PRIMARY KEY,
    header          text NOT NULL UNIQUE,
    description     text,
    attachment_file text,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id         INT REFERENCES users (id)
)