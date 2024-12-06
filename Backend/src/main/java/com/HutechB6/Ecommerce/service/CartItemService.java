package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.repository.ICartItemRepository;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import com.HutechB6.Ecommerce.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsFull(){

        return cartItemRepository.findAllCartItemsWithProductAndUser();
    }
    public Optional<CartItem> getCartById(Long id) {
        return cartItemRepository.findById(id);
    }

    public List<CartItem> getCartsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        return cartItemRepository.findByUser(user);
    }
    public List<CartItem> getCartsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return cartItemRepository.findByUser(user);
    }
    public CartItem addCart(String username, Long productId, int quantity) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with id " + username));
        List<CartItem> user_cart = cartItemRepository.findByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        for(CartItem c : user_cart){
            if(c.getProduct().equals(product)){

                return updateCart(c.getId(), username, c.getQuantity()+quantity);
            }
        }


        CartItem cart = new CartItem();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartItemRepository.save(cart);
    }

    public CartItem updateCart(Long id, String username, int quantity) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with id " + username));

        return cartItemRepository.findById(id)
                .map(cart -> {
                    cart.setUser(user);
                    cart.setQuantity(quantity);
                    return cartItemRepository.save(cart);
                })
                .orElseThrow(() -> new RuntimeException("Cart not found with id " + id));
    }

    public void deleteCart(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCart() {
        cartItemRepository.clear();
    }
}
