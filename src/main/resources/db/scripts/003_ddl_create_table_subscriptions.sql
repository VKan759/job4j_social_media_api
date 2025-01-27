CREATE TABLE if not exists posts
(
    id               serial PRIMARY KEY,
    subscriber_id    INT REFERENCES users (id) ON DELETE CASCADE,
    subscriber_id_to INT REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (subscriber_id, subscriber_id_to),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)