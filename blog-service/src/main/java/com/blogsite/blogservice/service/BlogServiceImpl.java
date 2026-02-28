package com.blogsite.blogservice.service;

import com.blogsite.blogservice.client.UserServiceClient;
import com.blogsite.blogservice.exception.BlogNotFoundException;
import com.blogsite.blogservice.model.Blog;
import com.blogsite.blogservice.model.UserDTO;
import com.blogsite.blogservice.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    /**
     * FEIGN CLIENT INJECTION
     * Instead of autowiring a UserRepository (wrong — that DB belongs to user-service),
     * we autowire the Feign client proxy. Spring generates the HTTP call automatically.
     */
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public Blog addBlog(String userName, Blog blog) {
        // Call user-service via Feign to verify the user exists and get their ID
        // This is an HTTP GET to http://user-service/api/v1.0/blogsite/user/internal/{userName}
        UserDTO user = userServiceClient.getUserByUserName(userName);

        blog.setUserId(user.getId());
        blog.setAuthorName(user.getUserName());
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(String blogName, String userName) {
        Blog blog = blogRepository.findByBlogNameIgnoreCase(blogName)
                .orElseThrow(() -> new BlogNotFoundException(
                        "Blog not found with name: " + blogName));

        // Call user-service to get the requesting user's ID for ownership check
        UserDTO requestingUser = userServiceClient.getUserByUserName(userName);

        if (!blog.getUserId().equals(requestingUser.getId())) {
            throw new SecurityException(
                    "User '" + userName + "' is not authorized to delete this blog.");
        }
        blogRepository.delete(blog);
    }

    @Override
    public List<Blog> getBlogsByUser(String userName) {
        // Resolve userName → userId via user-service Feign call
        UserDTO user = userServiceClient.getUserByUserName(userName);
        return blogRepository.findByUserId(user.getId());
    }

    @Override
    public Blog updateBlog(Long id, Blog updatedBlog) {
        Blog existing = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(
                        "Blog not found with id: " + id));
        existing.setBlogName(updatedBlog.getBlogName());
        existing.setCategory(updatedBlog.getCategory());
        existing.setArticle(updatedBlog.getArticle());
        existing.setAuthorName(updatedBlog.getAuthorName());
        return blogRepository.save(existing);
    }
}
