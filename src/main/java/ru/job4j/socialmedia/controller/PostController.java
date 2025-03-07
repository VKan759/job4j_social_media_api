package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.dto.Operations;
import ru.job4j.socialmedia.model.dto.PostDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.PostService;
import ru.job4j.socialmedia.service.UserService;

import java.util.List;
import java.util.Optional;
@Tag(name = "Post controller", description = "Controller for post managing")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Operation(summary = "Save new post", tags = {"post",
            "save"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PostDto.class), mediaType = "application")}),
    })
    @PostMapping
    public ResponseEntity<Post> save(@RequestBody @Valid PostDto post) {
        return ResponseEntity.ok(postService.save(post).orElse(null));
    }

    @Operation(summary = "Updating post", tags = {"post, update"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Post.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @Validated(Operations.OnUpdate.class)
    @PutMapping
    public ResponseEntity<Post> update(@RequestBody @Valid PostDto post) {
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

    @Operation(summary = "Getting post by Id", tags = {"post, get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Post.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Integer id) {
        ResponseEntity<Post> response = ResponseEntity.notFound().build();
        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            response = ResponseEntity.ok(postOpt.get());
        }
        return response;
    }

    @Operation(summary = "Deleting post by post id", tags = {"post", "delete"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        postService.delete(id);
    }

    @Operation(summary = "Getting posts by user Ids", tags = {"post", "get"})
    @GetMapping("/posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    public List<PostDto> getPostsByUserIds(@RequestParam List<Integer> userIds) {
        return postService.findAllByUserIds(userIds);
    }
}
