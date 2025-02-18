package ru.job4j.socialmedia.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Data
@Accessors(chain = true)
public class PostDto {

    private int id;
    private String header;
    private String description;
    private String attachmentFile;
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private int userId;
}
