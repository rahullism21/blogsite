package com.blogsite.blogservice.service;

import com.blogsite.blogservice.model.Tag;
import com.blogsite.blogservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {

    @Autowired private TagRepository tagRepository;

    /** UML: addTag() */
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /** UML: removeTag() */
    public void removeTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
