package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductImagesRepository extends JpaRepository<ProductImages, Long> {
    List<ProductImages> findByProductId(Long product_id);
}
