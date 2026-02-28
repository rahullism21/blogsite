package com.blogsite.blogservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;

/**
 * UML Entity: BlogPost
 * Fields  : postId, title, content, author: User, comments: List<Comment>, tags: List<Tag>
 * Methods : publishPost(), editPost(), deletePost(), getPostDetails()
 *
 * author  → stored as userId (Long) — cross-service, no JPA join to users_db
 * comments → owned by comment-service, not joined here
 * tags    → ManyToMany within blog-service (same DB)
 * category → ManyToOne within blog-service (same DB)
 */
@Entity
@Table(name = "blog_posts")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 20, message = "Title must be at least 20 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 1000, message = "Content must be at least 1000 characters")
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /** author (User) stored as plain Long — user-service owns users_db */
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    /** Denormalised author username for display without Feign call on every read */
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "blog_post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = java.time.LocalDateTime.now(); }
}
