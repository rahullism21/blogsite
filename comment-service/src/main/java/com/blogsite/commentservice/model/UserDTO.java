package com.blogsite.commentservice.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
}
