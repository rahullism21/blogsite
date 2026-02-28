package com.blogsite.blogservice.service;

import com.blogsite.blogservice.client.UserServiceClient;
import com.blogsite.blogservice.exception.BlogPostNotFoundException;
import com.blogsite.blogservice.model.*;
import com.blogsite.blogservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired private LikeRepository likeRepository;
    @Autowired private BlogPostRepository blogPostRepository;
    @Autowired private UserServiceClient userServiceClient;

    /** UML: addLike() */
    @Override
    public void addLike(Long postId, String username) {
        UserDTO user = userServiceClient.getByUsername(username);
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new BlogPostNotFoundException("Post not found: " + postId));

        if (!likeRepository.existsByUserIdAndPost_PostId(user.getUserId(), postId)) {
            Like like = Like.builder()
                    .userId(user.getUserId())
                    .post(post)
                    .build();
            likeRepository.save(like);
        }
    }

    /** UML: removeLike() */
    @Override
    public void removeLike(Long postId, String username) {
        UserDTO user = userServiceClient.getByUsername(username);
        likeRepository.findByUserIdAndPost_PostId(user.getUserId(), postId)
                .ifPresent(likeRepository::delete);
    }

    @Override
    public long getLikeCount(Long postId) {
        return likeRepository.countByPost_PostId(postId);
    }
}
