package com.blogsite.userservice.service;

import com.blogsite.userservice.model.LoginRequest;
import com.blogsite.userservice.model.UpdateProfileRequest;
import com.blogsite.userservice.model.User;

public interface UserService {
    User register(User user);              // UML: register()
    User login(LoginRequest request);      // UML: login()
    User updateProfile(Long userId, UpdateProfileRequest request); // UML: updateProfile()
    void deleteAccount(Long userId);       // UML: deleteAccount()
    User getById(Long userId);
    User getByUsername(String username);
}
