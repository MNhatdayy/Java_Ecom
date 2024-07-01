package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.model.Category;
import com.HutechB6.Ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.get();
    }
    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category updatedCategory) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(updatedCategory.getName());
            // Update other fields as needed
            categoryService.updateCategory(category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
}
