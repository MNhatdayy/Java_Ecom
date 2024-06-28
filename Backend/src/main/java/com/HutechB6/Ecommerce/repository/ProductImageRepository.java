package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
    List<ProductImages> findByProductId(Long product_id);
}
