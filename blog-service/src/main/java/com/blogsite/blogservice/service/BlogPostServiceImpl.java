package com.blogsite.blogservice.service;

import com.blogsite.blogservice.client.UserServiceClient;
import com.blogsite.blogservice.exception.BlogPostNotFoundException;
import com.blogsite.blogservice.model.*;
import com.blogsite.blogservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired private BlogPostRepository blogPostRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TagRepository tagRepository;
    @Autowired private UserServiceClient userServiceClient;

    /** UML: publishPost() */
    @Override
    public BlogPost publishPost(String username, BlogPost post, Long categoryId, List<Long> tagIds) {
        UserDTO user = userServiceClient.getByUsername(username);
        post.setAuthorId(user.getUserId());
        post.setAuthorName(user.getUsername());

        if (categoryId != null)
            categoryRepository.findById(categoryId).ifPresent(post::setCategory);

        if (tagIds != null && !tagIds.isEmpty()) {
            List<Tag> tags = tagIds.stream()
                    .map(id -> tagRepository.findById(id).orElse(null))
                    .filter(t -> t != null)
                    .collect(Collectors.toList());
            post.setTags(tags);
        }
        return blogPostRepository.save(post);
    }

    /** UML: editPost() */
    @Override
    public BlogPost editPost(Long postId, String username, BlogPost updated,
                              Long categoryId, List<Long> tagIds) {
        BlogPost existing = getPostDetails(postId);
        UserDTO user = userServiceClient.getByUsername(username);

        if (!existing.getAuthorId().equals(user.getUserId()))
            throw new SecurityException("Not authorized to edit this post.");

        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());

        if (categoryId != null)
            categoryRepository.findById(categoryId).ifPresent(existing::setCategory);

        if (tagIds != null) {
            List<Tag> tags = tagIds.stream()
                    .map(id -> tagRepository.findById(id).orElse(null))
                    .filter(t -> t != null)
                    .collect(Collectors.toList());
            existing.setTags(tags);
        }
        return blogPostRepository.save(existing);
    }

    /** UML: deletePost() */
    @Override
    public void deletePost(Long postId, String username) {
        BlogPost post = getPostDetails(postId);
        UserDTO user = userServiceClient.getByUsername(username);
        if (!post.getAuthorId().equals(user.getUserId()))
            throw new SecurityException("Not authorized to delete this post.");
        blogPostRepository.delete(post);
    }

    /** UML: getPostDetails() */
    @Override
    public BlogPost getPostDetails(Long postId) {
        return blogPostRepository.findById(postId)
                .orElseThrow(() -> new BlogPostNotFoundException("Post not found: " + postId));
    }

    @Override
    public List<BlogPost> getPostsByUser(String username) {
        UserDTO user = userServiceClient.getByUsername(username);
        return blogPostRepository.findByAuthorId(user.getUserId());
    }
}
