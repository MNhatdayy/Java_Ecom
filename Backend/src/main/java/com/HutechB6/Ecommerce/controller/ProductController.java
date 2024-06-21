package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.service.ProductService;
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
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private String uploadDir;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        //List<Product> productList = productService.getAllProducts();
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestPart("product") Product product, @RequestParam("imageUrl") MultipartFile imageUrl) {
        try {
            //Lưu ảnh vào thư mục images và cập nhật ảnh
            product.setImageUrl(saveImageStatic(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productService.addProduct(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductByIdWithImages(id);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestPart("product") Product productDetails, @RequestParam("imageUrl") MultipartFile imageUrl) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: "
                        + id));
        //Kiểm tra xem có ảnh gửi về không
        if(!imageUrl.getOriginalFilename().equals("blob")){
            //Nếu có sẽ cập nhật ảnh mới
            try {
                product.setImageUrl(saveImageStatic(imageUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Cập nhật các thuộc tính khác của Product
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());
        final Product updatedProduct =productService.addProduct(product);

        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: "+ id));
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
}
