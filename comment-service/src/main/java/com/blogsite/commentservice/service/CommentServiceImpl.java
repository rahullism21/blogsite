package com.blogsite.commentservice.service;

import com.blogsite.commentservice.client.UserServiceClient;
import com.blogsite.commentservice.model.Comment;
import com.blogsite.commentservice.model.UserDTO;
import com.blogsite.commentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private UserServiceClient userServiceClient;

    /** UML: addComment() */
    @Override
    public Comment addComment(Long postId, String username, String content) {
        UserDTO user = userServiceClient.getByUsername(username);
        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(user.getUserId())
                .authorName(user.getUsername())
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    /** UML: editComment() */
    @Override
    public Comment editComment(Long commentId, String username, String content) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));
        UserDTO user = userServiceClient.getByUsername(username);
        if (!existing.getAuthorId().equals(user.getUserId()))
            throw new SecurityException("Not authorized to edit this comment.");
        existing.setContent(content);
        return commentRepository.save(existing);
    }

    /** UML: deleteComment() */
    @Override
    public void deleteComment(Long commentId, String username) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));
        UserDTO user = userServiceClient.getByUsername(username);
        if (!existing.getAuthorId().equals(user.getUserId()))
            throw new SecurityException("Not authorized to delete this comment.");
        commentRepository.delete(existing);
    }

    @Override
    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByPostIdOrderByTimestampAsc(postId);
    }
}
