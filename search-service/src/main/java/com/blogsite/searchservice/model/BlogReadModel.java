package com.blogsite.searchservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * READ-ONLY view of BlogPost from blogs_db.
 * ddl-auto=validate ensures search-service never modifies the schema.
 */
@Entity
@Table(name = "blog_posts")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BlogReadModel {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryReadModel category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "blog_post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagReadModel> tags = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
