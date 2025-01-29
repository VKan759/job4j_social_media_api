package ru.job4j.socialmedia.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void init() {
        initPosts();
        initUsers();
        setPosts();
    }

    @Transactional
    public void initPosts() {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setHeader("header #" + i);
            post.setDescription("Description #" + i);
            post.setCreatedAt(LocalDateTime.now());
            posts.add(post);
        }
        postRepository.saveAll(posts);
    }

    @Transactional
    public void initUsers() {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUsername("user-" + i);
            user.setCreatedAt(LocalDateTime.now());
            user.setEmail(user.getUsername() + "@mail.ru");
            user.setPassword(user.getUsername());
            userRepository.save(user);
        }
    }

    @Transactional
    public void setPosts() {
        List<Post> posts = (List<Post>) postRepository.findAll();
        List<User> users = (List<User>) userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setPosts(List.of(posts.get(i)));
        }
        userRepository.saveAll(users);
    }
}
