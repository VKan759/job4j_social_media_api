package ru.job4j.socialmedia.service.impl;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
}
