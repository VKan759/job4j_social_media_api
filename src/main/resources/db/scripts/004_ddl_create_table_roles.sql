CREATE TABLE if not exists roles
(
    id               serial PRIMARY KEY,
    role text not null
);

insert into roles (role) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MODERATOR');