CREATE table if not exists users(
id serial primary key,
username text not null UNIQUE,
email text not null UNIQUE,
password text not null,
created_at TIMESTAMP default current_timestamp
)