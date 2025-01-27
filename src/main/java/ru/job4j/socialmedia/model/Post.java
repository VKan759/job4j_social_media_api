package ru.job4j.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String header;
    private String description;
    @JsonProperty(value = "attachment_file")
    private String attachmentFile;
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
}
