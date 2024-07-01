package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.repository.ICartItemRepository;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    @Autowired
    private ICartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsFull(){

        return cartItemRepository.findAllCartItemsWithProductAndUser();
    }

}
