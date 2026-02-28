package com.blogsite.searchservice.controller;

import com.blogsite.searchservice.model.BlogResponse;
import com.blogsite.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Search endpoints:
 *   GET /api/v1.0/blogsite/search/category/{category}
 *   GET /api/v1.0/blogsite/search/tag/{tag}
 *   GET /api/v1.0/blogsite/search/duration/{category}/{from}/{to}  ← Builder pattern
 *   GET /api/v1.0/blogsite/search/post/{postId}
 */
@RestController
@RequestMapping("/api/v1.0/blogsite/search")
public class SearchController {

    @Autowired private SearchService searchService;

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogResponse>> byCategory(@PathVariable String category) {
        return ResponseEntity.ok(searchService.getBlogsByCategory(category));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<BlogResponse>> byTag(@PathVariable String tag) {
        return ResponseEntity.ok(searchService.getBlogsByTag(tag));
    }

    /** Builder Creational Design Pattern applied here */
    @GetMapping("/duration/{category}/{from}/{to}")
    public ResponseEntity<List<BlogResponse>> byDuration(
            @PathVariable String category,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(searchService.getBlogsByCategoryAndDuration(category, from, to));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<BlogResponse> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(searchService.getPostDetail(postId));
    }
}
