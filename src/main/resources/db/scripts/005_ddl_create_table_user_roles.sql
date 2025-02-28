CREATE TABLE if not exists user_roles
(
    id               serial PRIMARY KEY,
    user_id int references users(id),
    role_id int references roles(id)
);
