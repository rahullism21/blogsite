package com.blogsite.userservice.model;

import jakarta.validation.constraints.*;
import lombok.*;

/** DTO for the updateProfile() UML method */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProfileRequest {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[^@]+@[^@]+\\.com$", message = "Email must contain '@' and '.com'")
    private String email;
}
