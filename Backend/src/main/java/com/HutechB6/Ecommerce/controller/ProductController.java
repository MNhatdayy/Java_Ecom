package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.model.Category;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.ProductImages;

import com.HutechB6.Ecommerce.service.CategoryService;
import com.HutechB6.Ecommerce.service.FirebaseService;
import com.HutechB6.Ecommerce.service.ProductImagesService;
import com.HutechB6.Ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private String uploadDir;
    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImagesService productImagesService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("all")
    public List<Product> getAllProducts(@Nullable @PathParam("category") String category) {
        List<Product> products = productService.getAllProducts();

        if (category == null || category.isEmpty()) {
            List<Product> temp = new ArrayList<>();
            for (Product product : products) {
                product.setCartItemList(null);
                product.setProductImages(null);
                product.setProductReviews(null);
                product.setFavouriteList(null);
                product.setOrderDetails(null);
                temp.add(product);
            }

            return temp;
        }

        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().getName().toLowerCase().equals(category)) {
                list.add(product);
            }
        }
        return list;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") String productString,
            @RequestPart("imageUrl") MultipartFile imageUrl,
            @RequestPart("images") List<MultipartFile> listimg,
            @RequestPart("categoryId") String CategoryId) throws IOException {

        // Parse the product JSON string to a Product object
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productString, Product.class);
        Category category = categoryService.getCategoryById(Long.parseLong(CategoryId)).orElseThrow();
        product.setCategory(category);

		if (imageUrl != null && !imageUrl.isEmpty()) {
//            String imagePath = saveImageStatic(imageUrl);
//            product.setImageUrl("/images/" + imagePath);
            String imagePath = firebaseService.uploadImages(imageUrl);
            product.setImageUrl(imagePath);
        }
        Product createdProduct = productService.addProduct(product);
        if (listimg != null && !listimg.isEmpty()) {
            for (MultipartFile file : listimg) {
                ProductImages productImages = new ProductImages();
                productImages.setProduct(createdProduct);
                productImages.setPathImage(saveImageStatic(file));
                productImagesService.addProductImage(productImages);
            }
        }
        return ResponseEntity.ok(createdProduct);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductByIdWithImages(id);
        product.setProductReviews(null);
        product.setProductImages(null);
        product.setOrderDetails(null);
        product.setFavouriteList(null);
        product.setCartItemList(null);
        return ResponseEntity.ok().body(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productString,
            @RequestPart(name = "imageUrl", required = false) MultipartFile imageUrl,
            @RequestPart(name = "images", required = false) List<MultipartFile> listimg,
            @RequestPart("categoryId") String categoryId) throws IOException {

        // Find the existing product
        Optional<Product> optionalExistingProduct = productService.getProductById(id);
        if (optionalExistingProduct.isEmpty()) {
            return ResponseEntity.notFound().build(); // Handle case where product with given id is not found
        }
        Product existingProduct = optionalExistingProduct.get();

        // Parse the product JSON string to update the existing product object
        ObjectMapper objectMapper = new ObjectMapper();
        Product updatedProduct = objectMapper.readValue(productString, Product.class);

        // Update the existing product with new values
        // Update only the necessary fields, not the whole object
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setPrice(updatedProduct.getPrice());
        // Update image URL if new image is provided
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String imagePath = saveImageStatic(imageUrl);
            existingProduct.setImageUrl("/images/" + imagePath);
        }

        // Update category
        Optional<Category> optionalCategory = categoryService.getCategoryById(Long.parseLong(categoryId));
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Handle case where category with given id is not found
        }
        existingProduct.setCategory(optionalCategory.get());

        // Update product entity
        Product updatedEntity = productService.updateProduct(existingProduct);

        // Handle additional images if provided
        if (listimg != null && !listimg.isEmpty()) {
            // Remove existing product images if needed
            productImagesService.deleteProductImages(updatedEntity);

            // Save new product images
            for (MultipartFile file : listimg) {
                ProductImages productImages = new ProductImages();
                productImages.setProduct(updatedEntity);
                productImages.setPathImage(saveImageStatic(file));
                productImagesService.addProductImage(productImages);
            }
        }

        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: "+ id));
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
    private String saveImageStatic(MultipartFile image) throws IOException {
        // Validate image file type (you can add more conditions as needed)
        String fileExtension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        if (!isValidImageType(fileExtension)) {
            throw new IOException("Invalid image type: " + fileExtension);
        }

        // Get the directory for saving images
        File saveDir = new ClassPathResource("/static/images").getFile();

        // Ensure the directory exists
        if (!saveDir.exists()) {
            saveDir.mkdirs();  // Create directories if they do not exist
        }

        // Generate a unique filename
        String fileName = UUID.randomUUID() + "." + fileExtension;

        // Create the full path for the new file
        Path path = Paths.get(saveDir.getAbsolutePath(), fileName);

        // Copy the image to the specified path
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    // Helper method to validate image file type
    private boolean isValidImageType(String extension) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif"};
        return Arrays.asList(validExtensions).contains(extension.toLowerCase());
    }
    @GetMapping("/category/{idCategory}")
    public ResponseEntity<List<Product>> GetProductByIdCategory(@PathVariable Long idCategory){
        List<Product> products = productService.getProductByIdCategory(idCategory);
        return ResponseEntity.ok(products);
    }
    @GetMapping
    public ResponseEntity<List<Product>> GetProductByIdCategory(@RequestParam("name") String name){
        List<Product> products = productService.getProductByName(name);
        return ResponseEntity.ok(products);
    }
}
