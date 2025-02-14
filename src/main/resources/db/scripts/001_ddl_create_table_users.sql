CREATE table if not exists users(
id serial primary key,
username text not null UNIQUE,
email text not null UNIQUE,
password text not null,
created_at TIMESTAMP default current_timestamp
);

insert into users (username, email, password)
VALUES ('username', 'user@mail.ru', 'password');