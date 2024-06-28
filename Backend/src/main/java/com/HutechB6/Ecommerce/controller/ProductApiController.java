package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.ProductImages;

import com.HutechB6.Ecommerce.service.ProductImageService;
import com.HutechB6.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductApiController {


    private String uploadDir;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public List<Product> getAllProducts() {
        //List<Product> productList = productService.getAllProducts();
        return productService.getAllProducts();
    }

    @PostMapping
//    public Product createProduct(@RequestPart("product") Product product, @RequestParam("imageUrl") MultipartFile imageUrl) {
//        Product productCreated = new Product();
//        try {
//            //Lưu ảnh đơn vào thư mục images và cập nhật ảnh
//            product.setImageUrl(saveImageStatic(imageUrl));
//            //Lưu sản phẩm mới
//            productCreated = productService.addProduct(product);
//            //Lưu các ảnh phụ
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return productCreated;
//    }
    public Product createProduct(@RequestPart("product") Product product, @RequestParam("imageUrl") MultipartFile imageUrl, @RequestParam("moreImages")List<MultipartFile> moreImages) {
        Product productCreated = new Product();
        try {
            //Lưu ảnh đơn vào thư mục images và cập nhật ảnh
            product.setImageUrl(saveImageStatic(imageUrl));
            //Lưu sản phẩm mới
            productCreated = productService.addProduct(product);
            //Lưu các ảnh phụ
            for(MultipartFile image : moreImages){

                ProductImages imageCreated =  productImageService.addProductImage(ProductImages.builder().PathImage(saveImageStatic(image)).product(productCreated).build());
                productImageService.addProductImage(imageCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productCreated;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductByIdWithImages(id);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
//                                                 @RequestPart("product") Product productDetails, @RequestParam("imageUrl") MultipartFile imageUrl) {
//
//        Product existingProduct = productService.getProductByIdWithImages(id);
//        //Kiểm tra xem có ảnh gửi về không
//        if(!imageUrl.getOriginalFilename().equals("blob")){
//            try {
//                existingProduct.setImageUrl(saveImageStatic(imageUrl));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        final Product updatedProduct =productService.updateProduct(productDetails, existingProduct);
//
//        return ResponseEntity.ok(updatedProduct);
//    }
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestPart("product") Product productDetails, @RequestParam("imageUrl") MultipartFile imageUrl, @RequestParam("moreImages")List<MultipartFile> moreImages) {

        Product existingProduct = productService.getProductByIdWithImages(id);
        //Kiểm tra xem có ảnh gửi về không
        if(!imageUrl.isEmpty()){
            try {
                existingProduct.setImageUrl(saveImageStatic(imageUrl));
                if(!moreImages.isEmpty()){

                    List<ProductImages> images = existingProduct.getProductImages().stream().toList();
                    for(int i=0; i<images.size(); i++){
                        //deleteImageStatic(images.get(i).getName());
                        images.get(i).setPathImage(saveImageStatic(moreImages.get(i)));
                        productImageService.updateProductImage(images.get(i));

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Product updatedProduct =productService.updateProduct(productDetails, existingProduct);

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
    private void deleteImageStatic(String filename) throws IOException{
        File saveFile = new ClassPathResource("static/images").getFile();
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + filename);
        Files.delete(path);

    }
}
