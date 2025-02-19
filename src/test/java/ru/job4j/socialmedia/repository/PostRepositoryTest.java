package ru.job4j.socialmedia.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void clearAll() {
        postRepository.deleteAll();
    }

    @Test
    void whenAddPostThenFindPostId() {
        Post post = new Post().setHeader("First header").setDescription("Description").setCreatedAt(LocalDateTime.now());
        post.setUser(new User().setUsername("username123").setPassword("password").setEmail("test123@test.com"));
        postRepository.save(post);
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getHeader()).isEqualTo("First header");
    }

    @Transactional
    @Test
    void whenFindAllThenReturnAllPosts() {
        User saved = userRepository.save(new User().setUsername("username1234").setPassword("password").setEmail("test1234@test.com"));
        Post first = new Post().setHeader("First header").setDescription("Description");
        first.setUser(saved);
        Post second = new Post().setHeader("second header").setDescription("Second description");
        second.setUser(saved);
        Post third = new Post().setHeader("third header").setDescription("Third description");
        third.setUser(saved);
        postRepository.saveAll(List.of(first, second, third));
        List<Post> all = postRepository.findAll();
        assertThat(all).hasSize(3);
        assertThat(all.get(0)).isEqualTo(first);
        assertThat(all).extracting(Post::getHeader).contains("second header");
    }
}