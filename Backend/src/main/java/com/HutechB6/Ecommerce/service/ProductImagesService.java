package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.ProductImages;
import com.HutechB6.Ecommerce.repository.IProductImagesRepository;
import jakarta.transaction.Transactional;
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
}
