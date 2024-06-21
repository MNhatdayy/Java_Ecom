package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductImagesRepository extends JpaRepository<ProductImages, Long> {
}
