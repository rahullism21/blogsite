package com.blogsite.blogservice.service;

public interface LikeService {
    void addLike(Long postId, String username);      // UML: addLike()
    void removeLike(Long postId, String username);   // UML: removeLike()
    long getLikeCount(Long postId);
}
