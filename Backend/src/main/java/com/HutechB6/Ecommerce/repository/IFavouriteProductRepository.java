package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.FavouriteProduct;
import com.HutechB6.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFavouriteProductRepository extends JpaRepository<FavouriteProduct, Long> {
    List<FavouriteProduct> findByUser(User user);

}
