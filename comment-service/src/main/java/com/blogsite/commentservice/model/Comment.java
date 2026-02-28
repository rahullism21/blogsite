package com.blogsite.commentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * UML Entity: Comment
 * Fields  : commentId, content, author: User, post: BlogPost, timestamp: DateTime
 * Methods : addComment(), editComment(), deleteComment()
 *
 * author → stored as authorId (Long) — cross-service reference to user-service
 * post   → stored as postId   (Long) — cross-service reference to blog-service
 */
@Entity
@Table(name = "comments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @NotBlank(message = "Comment content is mandatory")
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /** author: User — cross-service, stored as Long */
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    /** Denormalised for display */
    @Column(name = "author_name", nullable = false)
    private String authorName;

    /** post: BlogPost — cross-service, stored as Long */
    @Column(name = "post_id", nullable = false)
    private Long postId;

    /** UML field: timestamp: DateTime */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() { timestamp = LocalDateTime.now(); }
}
