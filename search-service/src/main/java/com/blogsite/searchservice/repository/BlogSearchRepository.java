package com.blogsite.searchservice.repository;

import com.blogsite.searchservice.model.BlogReadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogSearchRepository extends JpaRepository<BlogReadModel, Long> {

    List<BlogReadModel> findByCategory_NameIgnoreCase(String categoryName);

    List<BlogReadModel> findByTags_NameIgnoreCase(String tagName);

    @Query("SELECT b FROM BlogReadModel b WHERE LOWER(b.category.name) = LOWER(:category) " +
           "AND b.createdAt BETWEEN :from AND :to")
    List<BlogReadModel> findByCategoryAndDateRange(
            @Param("category") String category,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
