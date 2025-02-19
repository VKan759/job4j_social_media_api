package ru.job4j.socialmedia.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Accessors(chain = true)
public class PostDto {

    @NotBlank(groups = Operations.OnUpdate.class)
    private int id;
    @NotBlank
    private String header;
    @NotBlank
    private String description;
    private String attachmentFile;
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private int userId;
}
