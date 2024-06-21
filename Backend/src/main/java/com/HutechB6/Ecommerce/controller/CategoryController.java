package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.model.Category;
import com.HutechB6.Ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllProducts() {
        //List<Product> productList = productService.getAllProducts();
        return categoryService.getAllCategories();
    }
}
