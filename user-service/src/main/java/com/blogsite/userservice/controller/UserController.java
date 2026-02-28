package com.blogsite.userservice.controller;

import com.blogsite.userservice.model.*;
import com.blogsite.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * UML Methods exposed as REST:
 *   register()       → POST /api/v1.0/blogsite/user/register
 *   login()          → POST /api/v1.0/blogsite/user/login
 *   updateProfile()  → PUT  /api/v1.0/blogsite/user/profile/{userId}
 *   deleteAccount()  → DELETE /api/v1.0/blogsite/user/delete/{userId}
 *
 * Internal (Feign) endpoints — NOT in Gateway routing:
 *   GET /api/v1.0/blogsite/user/internal/{username}
 *   GET /api/v1.0/blogsite/user/internal/id/{userId}
 */
@RestController
@RequestMapping("/api/v1.0/blogsite/user")
public class UserController {

    @Autowired private UserService userService;

    // ── PUBLIC via API Gateway ──────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User saved = userService.register(user);
        saved.setPassword("****");
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);
        user.setPassword("****");
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable Long userId,
                                               @Valid @RequestBody UpdateProfileRequest request) {
        User updated = userService.updateProfile(userId, request);
        updated.setPassword("****");
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully.");
    }

    // ── INTERNAL (Feign clients from blog/comment/search/notification services) ─

    @GetMapping("/internal/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/internal/id/{userId}")
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }
}
