package com.blogsite.commentservice.controller;

import com.blogsite.commentservice.model.Comment;
import com.blogsite.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UML Methods:
 *   addComment()    → POST   /api/v1.0/blogsite/comments/add/{postId}
 *   editComment()   → PUT    /api/v1.0/blogsite/comments/edit/{commentId}
 *   deleteComment() → DELETE /api/v1.0/blogsite/comments/delete/{commentId}
 *   getComments()   → GET    /api/v1.0/blogsite/comments/post/{postId}
 */
@RestController
@RequestMapping("/api/v1.0/blogsite/comments")
public class CommentController {

    @Autowired private CommentService commentService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                               @RequestParam String username,
                                               @RequestParam String content) {
        return new ResponseEntity<>(
                commentService.addComment(postId, username, content),
                HttpStatus.CREATED);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable Long commentId,
                                                @RequestParam String username,
                                                @RequestParam String content) {
        return ResponseEntity.ok(commentService.editComment(commentId, username, content));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                 @RequestParam String username) {
        commentService.deleteComment(commentId, username);
        return ResponseEntity.ok("Comment deleted.");
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }
}
