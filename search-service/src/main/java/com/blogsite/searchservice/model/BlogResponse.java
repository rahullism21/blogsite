package com.blogsite.searchservice.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full BlogResponse DTO — composed by Builder pattern.
 * Combines Blog + Author(User) + Category + Tags.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BlogResponse {
    private Long postId;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Category details
    private Long categoryId;
    private String categoryName;

    // Tag names
    private List<String> tags;

    // Author details from user-service (Feign)
    private Long authorId;
    private String authorUsername;
    private String authorEmail;
}
