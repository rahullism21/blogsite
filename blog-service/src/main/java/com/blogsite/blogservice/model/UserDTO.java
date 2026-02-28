package com.blogsite.blogservice.model;

import lombok.*;

/** Local DTO — user data fetched from user-service via Feign */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
}
