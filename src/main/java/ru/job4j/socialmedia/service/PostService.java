package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostDto;

import java.util.Optional;

public interface PostService {
    Optional<Post> save(PostDto post);

    int update(PostDto post);

    int delete(int id);

    Optional<Post> findById(int id);
}