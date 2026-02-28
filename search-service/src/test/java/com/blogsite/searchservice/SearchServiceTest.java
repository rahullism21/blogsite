package com.blogsite.searchservice;

import com.blogsite.searchservice.client.UserServiceClient;
import com.blogsite.searchservice.model.BlogReadModel;
import com.blogsite.searchservice.model.BlogResponse;
import com.blogsite.searchservice.model.UserDTO;
import com.blogsite.searchservice.repository.BlogSearchRepository;
import com.blogsite.searchservice.service.SearchServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private BlogSearchRepository blogSearchRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private SearchServiceImpl searchService;

    private BlogReadModel blog;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        blog = BlogReadModel.builder()
                .id(1L)
                .blogName("Introduction to Spring Boot Microservices")
                .category("Java Spring Boot Technology Stack")
                .article("A".repeat(1000))
                .authorName("john_doe")
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .userName("john_doe")
                .userEmail("john@example.com")
                .build();
    }

    @Test
    @DisplayName("US04 - Should return blogs by category")
    void testGetBlogsByCategory() {
        when(blogSearchRepository.findByCategoryIgnoreCase("Java Spring Boot Technology Stack"))
                .thenReturn(List.of(blog));

        List<BlogReadModel> result = searchService.getBlogsByCategory(
                "Java Spring Boot Technology Stack");

        assertFalse(result.isEmpty());
        assertEquals("Java Spring Boot Technology Stack", result.get(0).getCategory());
    }

    @Test
    @DisplayName("US04 - Builder pattern assembles BlogResponse with author details from Feign")
    void testGetBlogsByCategoryAndDuration_WithAuthorDetails() {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        LocalDateTime to   = LocalDateTime.now();

        when(blogSearchRepository.findByCategoryAndDateRange(
                "Java Spring Boot Technology Stack", from, to))
                .thenReturn(List.of(blog));
        when(userServiceClient.getUserById(1L)).thenReturn(userDTO);

        List<BlogResponse> responses = searchService.getBlogsByCategoryAndDuration(
                "Java Spring Boot Technology Stack", from, to);

        assertFalse(responses.isEmpty());
        BlogResponse resp = responses.get(0);

        // Verify blog fields are set
        assertEquals("Introduction to Spring Boot Microservices", resp.getBlogName());

        // Verify author details came from Feign (user-service), not direct DB access
        assertEquals("john_doe", resp.getAuthorUserName());
        assertEquals("john@example.com", resp.getAuthorEmail());
        assertEquals(1L, resp.getAuthorId());

        verify(userServiceClient, times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("US04 - Should still return blogs if user-service is unavailable (partial fault tolerance)")
    void testGetBlogsByCategoryAndDuration_UserServiceDown() {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        LocalDateTime to   = LocalDateTime.now();

        when(blogSearchRepository.findByCategoryAndDateRange(
                "Java Spring Boot Technology Stack", from, to))
                .thenReturn(List.of(blog));
        when(userServiceClient.getUserById(1L))
                .thenThrow(new RuntimeException("user-service unavailable"));

        // Should NOT throw — partial fault tolerance
        List<BlogResponse> responses = searchService.getBlogsByCategoryAndDuration(
                "Java Spring Boot Technology Stack", from, to);

        assertFalse(responses.isEmpty());
        // Author fields will be null since user-service was down
        assertNull(responses.get(0).getAuthorEmail());
        // But blog data is still present
        assertEquals("Introduction to Spring Boot Microservices",
                responses.get(0).getBlogName());
    }
}
