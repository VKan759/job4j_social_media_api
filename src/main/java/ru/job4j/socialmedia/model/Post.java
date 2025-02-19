package ru.job4j.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import ru.job4j.socialmedia.model.dto.PostDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "posts")
@Data
@Accessors(chain = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String header;

    @NotBlank
    private String description;
    @JsonProperty(value = "attachment_file")
    private String attachmentFile;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "j_user_id", nullable = false)
    @JsonIgnore
    private User user;

    public static Post fromDto(PostDto postDto) {
        return new Post()
                .setId(postDto.getId())
                .setHeader(postDto.getHeader())
                .setDescription(postDto.getDescription())
                .setCreatedAt(postDto.getCreatedAt())
                .setAttachmentFile(postDto.getAttachmentFile());
    }

    public static PostDto toDto(Post post) {
        return new PostDto()
                .setId(post.getId())
                .setHeader(post.getHeader())
                .setDescription(post.getDescription())
                .setCreatedAt(post.getCreatedAt())
                .setAttachmentFile(post.getAttachmentFile());
    }
}
