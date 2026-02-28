package com.blogsite.blogservice.controller;

import com.blogsite.blogservice.model.*;
import com.blogsite.blogservice.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UML Methods:
 *   publishPost()    → POST   /api/v1.0/blogsite/posts/add
 *   editPost()       → PUT    /api/v1.0/blogsite/posts/edit/{postId}
 *   deletePost()     → DELETE /api/v1.0/blogsite/posts/delete/{postId}
 *   getPostDetails() → GET    /api/v1.0/blogsite/posts/{postId}  (via search-service too)
 *   addLike()        → POST   /api/v1.0/blogsite/posts/like/{postId}
 *   removeLike()     → DELETE /api/v1.0/blogsite/posts/unlike/{postId}
 *
 *   addCategory()    → POST   /api/v1.0/blogsite/categories
 *   editCategory()   → PUT    /api/v1.0/blogsite/categories/{id}
 *   deleteCategory() → DELETE /api/v1.0/blogsite/categories/{id}
 *
 *   addTag()         → POST   /api/v1.0/blogsite/tags
 *   removeTag()      → DELETE /api/v1.0/blogsite/tags/{id}
 */
@RestController
public class BlogController {

    @Autowired private BlogPostService blogPostService;
    @Autowired private LikeService likeService;
    @Autowired private CategoryService categoryService;
    @Autowired private TagService tagService;

    // ── BLOG POST CRUD ──────────────────────────────────────────────────

    @PostMapping("/api/v1.0/blogsite/posts/add")
    public ResponseEntity<BlogPost> publishPost(
            @RequestParam String username,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> tagIds,
            @Valid @RequestBody BlogPost post) {
        return new ResponseEntity<>(
                blogPostService.publishPost(username, post, categoryId, tagIds),
                HttpStatus.CREATED);
    }

    @PutMapping("/api/v1.0/blogsite/posts/edit/{postId}")
    public ResponseEntity<BlogPost> editPost(
            @PathVariable Long postId,
            @RequestParam String username,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> tagIds,
            @Valid @RequestBody BlogPost post) {
        return ResponseEntity.ok(
                blogPostService.editPost(postId, username, post, categoryId, tagIds));
    }

    @DeleteMapping("/api/v1.0/blogsite/posts/delete/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long postId,
            @RequestParam String username) {
        blogPostService.deletePost(postId, username);
        return ResponseEntity.ok("Post deleted successfully.");
    }

    @GetMapping("/api/v1.0/blogsite/posts/user/{username}")
    public ResponseEntity<List<BlogPost>> getPostsByUser(@PathVariable String username) {
        return ResponseEntity.ok(blogPostService.getPostsByUser(username));
    }

    // ── LIKES ──────────────────────────────────────────────────────────

    @PostMapping("/api/v1.0/blogsite/posts/like/{postId}")
    public ResponseEntity<String> addLike(
            @PathVariable Long postId,
            @RequestParam String username) {
        likeService.addLike(postId, username);
        return ResponseEntity.ok("Post liked. Total likes: " + likeService.getLikeCount(postId));
    }

    @DeleteMapping("/api/v1.0/blogsite/posts/unlike/{postId}")
    public ResponseEntity<String> removeLike(
            @PathVariable Long postId,
            @RequestParam String username) {
        likeService.removeLike(postId, username);
        return ResponseEntity.ok("Like removed. Total likes: " + likeService.getLikeCount(postId));
    }

    // ── CATEGORIES ─────────────────────────────────────────────────────

    @GetMapping("/api/v1.0/blogsite/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/api/v1.0/blogsite/categories")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/api/v1.0/blogsite/categories/{categoryId}")
    public ResponseEntity<Category> editCategory(@PathVariable Long categoryId,
                                                   @Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.editCategory(categoryId, category));
    }

    @DeleteMapping("/api/v1.0/blogsite/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted.");
    }

    // ── TAGS ──────────────────────────────────────────────────────────

    @GetMapping("/api/v1.0/blogsite/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/api/v1.0/blogsite/tags")
    public ResponseEntity<Tag> addTag(@Valid @RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.addTag(tag), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1.0/blogsite/tags/{tagId}")
    public ResponseEntity<String> removeTag(@PathVariable Long tagId) {
        tagService.removeTag(tagId);
        return ResponseEntity.ok("Tag removed.");
    }
}
