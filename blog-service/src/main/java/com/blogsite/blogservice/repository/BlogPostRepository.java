package com.blogsite.blogservice.repository;

import com.blogsite.blogservice.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByAuthorId(Long authorId);
    List<BlogPost> findByCategory_CategoryId(Long categoryId);
    List<BlogPost> findByTags_TagId(Long tagId);
}
