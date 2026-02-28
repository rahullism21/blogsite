package com.blogsite.searchservice.client;

import com.blogsite.searchservice.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/v1.0/blogsite/user/internal/id/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
