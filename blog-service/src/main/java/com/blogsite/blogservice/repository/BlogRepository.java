package com.blogsite.blogservice.repository;

import com.blogsite.blogservice.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId);
    Optional<Blog> findByBlogNameIgnoreCase(String blogName);
}
