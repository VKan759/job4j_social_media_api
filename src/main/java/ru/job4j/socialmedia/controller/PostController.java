package ru.job4j.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.PostService;
import ru.job4j.socialmedia.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody PostDto post) {
        return ResponseEntity.ok(postService.save(post).orElse(null));
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody PostDto post) {
        int update = postService.update(post);
        Optional<User> byId = userService.findById(post.getUserId());
        Post postFromDto = null;
        if (update > 0) {
            postFromDto = Post.fromDto(post);
            postFromDto.setUser(byId.orElse(null));
        }
        return ResponseEntity.ok(postFromDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable int id) {
        return ResponseEntity.ok(postService.findById(id).orElse(null));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        postService.delete(id);
    }
}
