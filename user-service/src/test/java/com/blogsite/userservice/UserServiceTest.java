package com.blogsite.userservice;

import com.blogsite.userservice.exception.UserAlreadyExistsException;
import com.blogsite.userservice.model.User;
import com.blogsite.userservice.repository.UserRepository;
import com.blogsite.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User.builder()
                .id(1L)
                .userName("john_doe")
                .userEmail("john@example.com")
                .password("John1234")
                .build();
    }

    @Test
    @DisplayName("US01 - Should register user successfully")
    void testRegisterUser_Success() {
        when(userRepository.existsByUserName("john_doe")).thenReturn(false);
        when(userRepository.existsByUserEmail("john@example.com")).thenReturn(false);
        when(userRepository.save(validUser)).thenReturn(validUser);

        User result = userService.registerUser(validUser);

        assertNotNull(result);
        assertEquals("john_doe", result.getUserName());
        verify(userRepository, times(1)).save(validUser);
    }

    @Test
    @DisplayName("US01 - Should throw exception when username already exists")
    void testRegisterUser_DuplicateUsername() {
        when(userRepository.existsByUserName("john_doe")).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(validUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("US01 - Should throw exception when email already exists")
    void testRegisterUser_DuplicateEmail() {
        when(userRepository.existsByUserName("john_doe")).thenReturn(false);
        when(userRepository.existsByUserEmail("john@example.com")).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(validUser));
        verify(userRepository, never()).save(any());
    }
}
