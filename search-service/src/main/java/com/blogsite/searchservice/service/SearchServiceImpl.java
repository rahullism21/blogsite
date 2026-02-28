package com.blogsite.searchservice.service;

import com.blogsite.searchservice.builder.BlogResponseBuilder;
import com.blogsite.searchservice.client.UserServiceClient;
import com.blogsite.searchservice.model.*;
import com.blogsite.searchservice.repository.BlogSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired private BlogSearchRepository blogSearchRepository;
    @Autowired private UserServiceClient userServiceClient;

    private BlogResponse toResponse(BlogReadModel blog) {
        UserDTO author = null;
        try { author = userServiceClient.getUserById(blog.getAuthorId()); }
        catch (Exception ignored) {} // partial fault tolerance
        return BlogResponseBuilder.newInstance()
                .fromBlog(blog)
                .withAuthor(author)
                .build();
    }

    @Override
    public List<BlogResponse> getBlogsByCategory(String category) {
        return blogSearchRepository.findByCategory_NameIgnoreCase(category)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<BlogResponse> getBlogsByTag(String tag) {
        return blogSearchRepository.findByTags_NameIgnoreCase(tag)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<BlogResponse> getBlogsByCategoryAndDuration(String category,
                                                             LocalDateTime from,
                                                             LocalDateTime to) {
        return blogSearchRepository.findByCategoryAndDateRange(category, from, to)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public BlogResponse getPostDetail(Long postId) {
        BlogReadModel blog = blogSearchRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
        return toResponse(blog);
    }
}
