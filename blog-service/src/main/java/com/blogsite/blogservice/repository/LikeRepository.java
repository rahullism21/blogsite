package com.blogsite.blogservice.repository;

import com.blogsite.blogservice.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPost_PostId(Long userId, Long postId);
    long countByPost_PostId(Long postId);
    boolean existsByUserIdAndPost_PostId(Long userId, Long postId);
}
