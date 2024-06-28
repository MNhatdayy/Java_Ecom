package com.HutechB6.Ecommerce.repository;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product JOIN FETCH ci.user")
    List<CartItem> findAllCartItemsWithProductAndUser();
}
