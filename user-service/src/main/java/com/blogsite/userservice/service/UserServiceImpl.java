package com.blogsite.userservice.service;

import com.blogsite.userservice.exception.UserAlreadyExistsException;
import com.blogsite.userservice.exception.UserNotFoundException;
import com.blogsite.userservice.model.LoginRequest;
import com.blogsite.userservice.model.UpdateProfileRequest;
import com.blogsite.userservice.model.User;
import com.blogsite.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;

    @Override
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' is already taken.");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException("Email '" + user.getEmail() + "' is already registered.");
        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequest request) {
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password."));
    }

    @Override
    public User updateProfile(Long userId, UpdateProfileRequest request) {
        User existing = getById(userId);
        existing.setUsername(request.getUsername());
        existing.setEmail(request.getEmail());
        return userRepository.save(existing);
    }

    @Override
    public void deleteAccount(Long userId) {
        User user = getById(userId);
        userRepository.delete(user);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }
}
