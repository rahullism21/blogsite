package com.blogsite.commentservice.service;

import com.blogsite.commentservice.model.Comment;
import java.util.List;

public interface CommentService {
    Comment addComment(Long postId, String username, String content);   // UML: addComment()
    Comment editComment(Long commentId, String username, String content); // UML: editComment()
    void deleteComment(Long commentId, String username);                 // UML: deleteComment()
    List<Comment> getCommentsForPost(Long postId);
}
