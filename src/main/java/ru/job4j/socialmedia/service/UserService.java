package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.User;

import java.util.Optional;

public interface UserService {
    User create(User user);

    User update(User user);

    Optional<User> findById(int id);

    void delete(int id);
}
