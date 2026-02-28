package com.blogsite.searchservice.model;

import jakarta.persistence.*;
import lombok.*;

/** Read-only Category entity for search-service */
@Entity
@Table(name = "categories")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryReadModel {

    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
