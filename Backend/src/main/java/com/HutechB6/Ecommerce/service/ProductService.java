package com.HutechB6.Ecommerce.service;


import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final IProductRepository productRepository;
    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // Lấy product và tất cả ảnh của nó
    public Product getProductByIdWithImages(Long id) {
        return productRepository.findProductWithImagesById(id);
    }
    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    // Add a new product to the database
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    // Update an existing product
    public Product updateProduct( @NotNull Product existingProduct) {
//
        return productRepository.save(existingProduct);
    }
    public void deleteProductById(Long id){
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
}
