package com.blogsite.blogservice.service;

import com.blogsite.blogservice.model.Blog;
import java.util.List;

public interface BlogService {
    Blog addBlog(String userName, Blog blog);
    void deleteBlog(String blogName, String userName);
    List<Blog> getBlogsByUser(String userName);
    Blog updateBlog(Long id, Blog updatedBlog);
}
