package ru.job4j.socialmedia.service;

import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> save(PostDto post);

    int update(PostDto post);

    int delete(int id);

    Optional<Post> findById(int id);

    List<PostDto> findAllByUserIds(List<Integer> userIds);
}