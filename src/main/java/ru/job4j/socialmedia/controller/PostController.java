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
        ResponseEntity<Post> response = ResponseEntity.notFound().build();
        int update = postService.update(post);
        Optional<User> userOpt = userService.findById(post.getUserId());
        if (update > 0) {
            Post postFromDto = Post.fromDto(post);
            postFromDto.setUser(userOpt.orElse(null));
            response = ResponseEntity.ok(postFromDto);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable int id) {
        ResponseEntity<Post> response = ResponseEntity.notFound().build();
        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            response = ResponseEntity.ok(postOpt.get());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        postService.delete(id);
    }
}
