package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void clearAll() {
        postRepository.deleteAll();
    }

    @Test
    void whenAddPostThenFindPostId() {
        Post post = new Post().setHeader("First header").setDescription("Description").setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getHeader()).isEqualTo("First header");
    }

    @Test
    void whenFindAllThenReturnAllPosts() {
        Post first = new Post().setHeader("First header").setDescription("Description");
        Post second = new Post().setHeader("second header").setDescription("Second description");
        Post third = new Post().setHeader("third header").setDescription("Third description");
        postRepository.saveAll(List.of(first, second, third));
        List<Post> all = (List<Post>) postRepository.findAll();
        assertThat(all).hasSize(3);
        assertThat(all.get(0)).isEqualTo(first);
        assertThat(all).extracting(Post::getHeader).contains("second header");
    }
}