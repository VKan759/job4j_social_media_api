CREATE TABLE if not exists roles
(
    id               serial PRIMARY KEY,
    name text not null
);

insert into roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MODERATOR');