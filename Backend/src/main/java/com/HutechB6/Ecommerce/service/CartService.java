package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private IProductRepository productRepository;

    public void addToCart(Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        for(CartItem item : cartItems){
            if(item.getProduct().getId().equals(product.getId())){
                item.setQuantity(item.getQuantity()+quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }
    public void updateCart(Long productId, int quantity) {
        // Logic để cập nhật số lượng sản phẩm trong giỏ hàng
        for(CartItem c : cartItems){
            if(c.getProduct().getId().equals(productId)){
                c.setQuantity(quantity);
            }
        }

    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clearCart() {
        cartItems.clear();
    }
}
