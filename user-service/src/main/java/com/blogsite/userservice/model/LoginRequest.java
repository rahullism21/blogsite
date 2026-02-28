package com.blogsite.userservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/** DTO for the login() UML method */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginRequest {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
