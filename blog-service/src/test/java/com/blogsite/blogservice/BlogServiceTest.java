package com.blogsite.blogservice;

import com.blogsite.blogservice.client.UserServiceClient;
import com.blogsite.blogservice.exception.BlogNotFoundException;
import com.blogsite.blogservice.model.Blog;
import com.blogsite.blogservice.model.UserDTO;
import com.blogsite.blogservice.repository.BlogRepository;
import com.blogsite.blogservice.service.BlogServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserServiceClient userServiceClient;  // Mocked Feign client

    @InjectMocks
    private BlogServiceImpl blogService;

    private UserDTO userDTO;
    private Blog blog;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .id(1L)
                .userName("john_doe")
                .userEmail("john@example.com")
                .build();

        blog = Blog.builder()
                .id(1L)
                .blogName("Introduction to Spring Boot Microservices")
                .category("Java Spring Boot Technology Stack")
                .article("A".repeat(1000))
                .authorName("john_doe")
                .userId(1L)
                .build();
    }

    @Test
    @DisplayName("US02 - Should add blog after verifying user via Feign")
    void testAddBlog_Success() {
        when(userServiceClient.getUserByUserName("john_doe")).thenReturn(userDTO);
        when(blogRepository.save(blog)).thenReturn(blog);

        Blog result = blogService.addBlog("john_doe", blog);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        // Verify Feign was called — confirming no DB cross-access
        verify(userServiceClient, times(1)).getUserByUserName("john_doe");
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    @DisplayName("US03 - Should delete blog when owner requests")
    void testDeleteBlog_OwnerSuccess() {
        when(blogRepository.findByBlogNameIgnoreCase(blog.getBlogName()))
                .thenReturn(Optional.of(blog));
        when(userServiceClient.getUserByUserName("john_doe")).thenReturn(userDTO);

        assertDoesNotThrow(() -> blogService.deleteBlog(blog.getBlogName(), "john_doe"));
        verify(blogRepository).delete(blog);
    }

    @Test
    @DisplayName("US03 - Should throw SecurityException when non-owner tries to delete")
    void testDeleteBlog_NotOwner() {
        UserDTO otherUser = UserDTO.builder().id(99L).userName("hacker").build();
        when(blogRepository.findByBlogNameIgnoreCase(blog.getBlogName()))
                .thenReturn(Optional.of(blog));
        when(userServiceClient.getUserByUserName("hacker")).thenReturn(otherUser);

        assertThrows(SecurityException.class,
                () -> blogService.deleteBlog(blog.getBlogName(), "hacker"));
        verify(blogRepository, never()).delete(any());
    }

    @Test
    @DisplayName("US03 - Should throw BlogNotFoundException for non-existent blog")
    void testDeleteBlog_NotFound() {
        when(blogRepository.findByBlogNameIgnoreCase("nonexistent"))
                .thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class,
                () -> blogService.deleteBlog("nonexistent", "john_doe"));
    }

    @Test
    @DisplayName("US03 - Should return all blogs for user via Feign userId lookup")
    void testGetBlogsByUser() {
        when(userServiceClient.getUserByUserName("john_doe")).thenReturn(userDTO);
        when(blogRepository.findByUserId(1L)).thenReturn(List.of(blog));

        List<Blog> result = blogService.getBlogsByUser("john_doe");

        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getUserId());
        // Confirm we called user-service (Feign), not a cross-DB join
        verify(userServiceClient).getUserByUserName("john_doe");
    }
}
