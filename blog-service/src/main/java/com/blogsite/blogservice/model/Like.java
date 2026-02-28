package com.blogsite.blogservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * UML Entity: Like
 * Fields  : likeId, user: User, post: BlogPost
 * Methods : addLike(), removeLike()
 *
 * user → stored as userId (Long) — cross-service reference
 * post → ManyToOne within blog-service
 * Unique constraint: one user can like a post only once
 */
@Entity
@Table(name = "likes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    /** User who liked — stored as plain Long (cross-service) */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private BlogPost post;
}
