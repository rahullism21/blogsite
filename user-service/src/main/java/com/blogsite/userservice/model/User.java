package com.blogsite.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * UML Entity: User
 * Fields  : userId, username, email, password, posts: List<BlogPost>
 * Methods : register(), login(), updateProfile(), deleteAccount()
 *
 * posts is a cross-service relation → NOT a JPA join here.
 */
@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[^@]+@[^@]+\\.com$",
             message = "Email must contain '@' and '.com'")
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
             message = "Password must be alphanumeric and at least 8 characters")
    @Column(name = "password", nullable = false)
    private String password;
}
