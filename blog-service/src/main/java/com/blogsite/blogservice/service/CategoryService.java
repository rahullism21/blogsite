package com.blogsite.blogservice.service;

import com.blogsite.blogservice.model.Category;
import com.blogsite.blogservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired private CategoryRepository categoryRepository;

    /** UML: addCategory() */
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    /** UML: editCategory() */
    public Category editCategory(Long categoryId, Category updated) {
        Category existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return categoryRepository.save(existing);
    }

    /** UML: deleteCategory() */
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
    }
}
