package com.blogsite.blogservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * UML Entity: Category
 * Fields  : categoryId, name, description, posts: List<BlogPost>
 * Methods : addCategory(), editCategory(), deleteCategory()
 */
@Entity
@Table(name = "categories")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotBlank(message = "Category name is mandatory")
    @Size(min = 20, message = "Category name must be at least 20 characters")
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;
}
