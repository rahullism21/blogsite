package com.blogsite.blogservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Blog entity — stored in blogs_db (owned exclusively by blog-service).
 *
 * KEY MICROSERVICE DESIGN NOTE:
 * We do NOT store a @ManyToOne User object here because User belongs to user-service.
 * Instead, we store the userId as a plain Long. If we need user details, we call
 * user-service via the UserServiceClient Feign interface.
 */
@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Blog name is mandatory")
    @Size(min = 20, message = "Blog name must be at least 20 characters")
    @Column(name = "blog_name", nullable = false)
    private String blogName;

    @NotBlank(message = "Category is mandatory")
    @Size(min = 20, message = "Category must be at least 20 characters")
    @Column(name = "category", nullable = false)
    private String category;

    @NotBlank(message = "Article is mandatory")
    @Size(min = 1000, message = "Article must be at least 1000 characters")
    @Column(name = "article", columnDefinition = "TEXT", nullable = false)
    private String article;

    @NotBlank(message = "Author name is mandatory")
    @Column(name = "author_name", nullable = false)
    private String authorName;

    /**
     * We store the userId from user-service as a plain Long.
     * We do NOT join to a User table — that database belongs to another service.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
