package com.blogsite.blogservice.client;

import com.blogsite.blogservice.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1.0/blogsite/user/internal/{username}")
    UserDTO getByUsername(@PathVariable("username") String username);

    @GetMapping("/api/v1.0/blogsite/user/internal/id/{userId}")
    UserDTO getById(@PathVariable("userId") Long userId);
}
