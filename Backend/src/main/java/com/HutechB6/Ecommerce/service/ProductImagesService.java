package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.ProductImages;
import com.HutechB6.Ecommerce.repository.IProductImagesRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImagesService {
    private final IProductImagesRepository productImageRepository;
    public ProductImages addProductImage(ProductImages productImage) {
        return productImageRepository.save(productImage);
    }
    public List<ProductImages> GetProductImages(){

        return productImageRepository.findAll();
    }
    public List<ProductImages> findByProductId(Long product_id){
        return productImageRepository.findByProductId(product_id);
    }
    public void updateProductImage(@NotNull ProductImages image) {

        ProductImages existingProductImage = productImageRepository.findById(image.getId())
                .orElseThrow(() -> new IllegalStateException("ProductImage with ID " +
                        image.getId() + " does not exist."));
        existingProductImage.setPathImage(image.getPathImage());
        productImageRepository.save(existingProductImage);
    }

    public void deleteProductImages(Product updatedEntity) {
    }
}
