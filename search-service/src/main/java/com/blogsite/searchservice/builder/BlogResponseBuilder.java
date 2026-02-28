package com.blogsite.searchservice.builder;

import com.blogsite.searchservice.model.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CREATIONAL DESIGN PATTERN: Builder
 * Assembles BlogResponse from BlogReadModel + UserDTO (from Feign)
 * including Category and Tag details from the UML diagram.
 */
public class BlogResponseBuilder {

    private Long postId;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private String categoryName;
    private List<String> tags;
    private Long authorId;
    private String authorUsername;
    private String authorEmail;

    private BlogResponseBuilder() {}

    public static BlogResponseBuilder newInstance() { return new BlogResponseBuilder(); }

    public BlogResponseBuilder fromBlog(BlogReadModel blog) {
        this.postId      = blog.getPostId();
        this.title       = blog.getTitle();
        this.content     = blog.getContent();
        this.authorName  = blog.getAuthorName();
        this.createdAt   = blog.getCreatedAt();
        this.updatedAt   = blog.getUpdatedAt();

        if (blog.getCategory() != null) {
            this.categoryId   = blog.getCategory().getCategoryId();
            this.categoryName = blog.getCategory().getName();
        }

        if (blog.getTags() != null) {
            this.tags = blog.getTags().stream()
                    .map(TagReadModel::getName)
                    .collect(Collectors.toList());
        }
        return this;
    }

    public BlogResponseBuilder withAuthor(UserDTO user) {
        if (user != null) {
            this.authorId       = user.getUserId();
            this.authorUsername = user.getUsername();
            this.authorEmail    = user.getEmail();
        }
        return this;
    }

    public BlogResponse build() {
        BlogResponse r = new BlogResponse();
        r.setPostId(postId);         r.setTitle(title);
        r.setContent(content);       r.setAuthorName(authorName);
        r.setCreatedAt(createdAt);   r.setUpdatedAt(updatedAt);
        r.setCategoryId(categoryId); r.setCategoryName(categoryName);
        r.setTags(tags);             r.setAuthorId(authorId);
        r.setAuthorUsername(authorUsername); r.setAuthorEmail(authorEmail);
        return r;
    }
}
