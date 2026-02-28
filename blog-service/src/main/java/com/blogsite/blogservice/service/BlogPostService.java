package com.blogsite.blogservice.service;

import com.blogsite.blogservice.model.*;
import java.util.List;

public interface BlogPostService {
    // UML: publishPost()
    BlogPost publishPost(String username, BlogPost post, Long categoryId, List<Long> tagIds);
    // UML: editPost()
    BlogPost editPost(Long postId, String username, BlogPost updated, Long categoryId, List<Long> tagIds);
    // UML: deletePost()
    void deletePost(Long postId, String username);
    // UML: getPostDetails()
    BlogPost getPostDetails(Long postId);
    // view all posts by user
    List<BlogPost> getPostsByUser(String username);
}
