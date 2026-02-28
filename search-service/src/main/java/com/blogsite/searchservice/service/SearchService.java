package com.blogsite.searchservice.service;

import com.blogsite.searchservice.model.BlogResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchService {
    List<BlogResponse> getBlogsByCategory(String category);
    List<BlogResponse> getBlogsByTag(String tag);
    List<BlogResponse> getBlogsByCategoryAndDuration(String category, LocalDateTime from, LocalDateTime to);
    BlogResponse getPostDetail(Long postId);
}
