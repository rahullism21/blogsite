package com.blogsite.blogservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * UML Entity: Tag
 * Fields  : tagId, name, posts: List<BlogPost>
 * Methods : addTag(), removeTag()
 */
@Entity
@Table(name = "tags")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @NotBlank(message = "Tag name is mandatory")
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;
}
