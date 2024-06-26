package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findProductWithImagesById(Long id);
}
