package ru.job4j.socialmedia.service.impl;

import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.dto.PostDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Post> save(PostDto post) {
        Optional<User> userById = userRepository.findById(post.getUserId());
        Optional<Post> postToSave = Optional.empty();
        if (userById.isPresent()) {
            postToSave = Optional.of(new Post()
                    .setHeader(post.getHeader())
                    .setDescription(post.getDescription())
                    .setUser(userById.get())
                    .setAttachmentFile(post.getAttachmentFile())
            );
            postRepository.save(postToSave.get());
        }
        return postToSave;
    }

    @Override
    public int update(PostDto post) {
        return postRepository.updatePost(post.getId(), post.getHeader(), post.getDescription());
    }

    @Override
    public int delete(int id) {
        return postRepository.deletePost(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<PostDto> findAllByUserIds(List<Integer> userIds) {
        return postRepository.findAllByUserIdIn(userIds).stream().map(Post::toDto).toList();
    }
}
