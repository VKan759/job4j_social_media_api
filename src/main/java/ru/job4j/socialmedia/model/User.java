package ru.job4j.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.job4j.socialmedia.model.dto.Operations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(groups = Operations.OnUpdate.class)
    private int id;

    @NotBlank
    private String username;

    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "friendship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new LinkedHashSet<>();
}
