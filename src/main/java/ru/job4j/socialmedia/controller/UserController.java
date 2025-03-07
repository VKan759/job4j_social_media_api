package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.model.dto.Operations;
import ru.job4j.socialmedia.service.UserService;

import java.util.Optional;

@Tag(name = "User controller", description = "Controller for user managing")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Поиск user по id",
            description = "В пути запроса передается id пользователя, по котору выполняется поиск",
            tags = {"User", "find"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = User.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) {
        ResponseEntity<User> response = ResponseEntity.notFound().build();
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            response = ResponseEntity.ok(userOpt.get());
        }
        return response;
    }

    @Operation(summary = "Creating user",
            description = "Создание пользователя в соответствии с условиями валидации. Данные для создания пользователя передатся в теле запроса",
            tags = {"User", "create"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User saved = userService.create(user);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Update user",
            tags = {"User", "update"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class),
                    mediaType = "applications/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @Validated(Operations.OnUpdate.class)
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updated = userService.update(user);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete user by Id",
            tags = {"User", "delete"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }
}
