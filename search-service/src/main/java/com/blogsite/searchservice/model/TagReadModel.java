package com.blogsite.searchservice.model;

import jakarta.persistence.*;
import lombok.*;

/** Read-only Tag entity for search-service */
@Entity
@Table(name = "tags")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TagReadModel {

    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "name")
    private String name;
}
