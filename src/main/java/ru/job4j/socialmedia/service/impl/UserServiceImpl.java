package ru.job4j.socialmedia.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.service.UserService;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        boolean existsById = userRepository.existsById(user.getId());
        if (existsById) {
            throw new IllegalArgumentException("User already exists!");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        boolean existsById = userRepository.existsById(user.getId());
        if (!existsById) {
            throw new EntityNotFoundException("User does not exist!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
