package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.CartRequest;
import com.HutechB6.Ecommerce.DTO.CartUpdateRequest;
import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.service.CartItemService;
import com.HutechB6.Ecommerce.service.ProductImagesService;
import com.HutechB6.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImagesService productImageService;
    @GetMapping
    public List<CartItem> getAllCartItems(){
        return cartItemService.getCartItemsFull();
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<CartItem>> getCartsByUsername(@PathVariable String username) {
        List<CartItem> carts = cartItemService.getCartsByUsername(username);
        List<CartItem> temp = new ArrayList<>();
        for(CartItem t : carts){
            Product p = t.getProduct();
            p.setCartItemList(null);
            p.setFavouriteList(null);
            p.setOrderDetails(null);
            p.setProductReviews(null);
            p.setProductImages(null);

            t.setProduct(p);
            t.setUser(null);
            temp.add(t);
        }
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItem> addCart(@RequestBody CartRequest cartRequest) {
        CartItem createdCart = cartItemService.addCart(cartRequest.getUsername(), cartRequest.getProductId(), cartRequest.getQuantity());
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCart(@PathVariable Long id, @RequestBody CartUpdateRequest cartRequest) {
        try {
            CartItem updatedCart = cartItemService.updateCart(id, cartRequest.getUsername(), cartRequest.getQuantity());
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartItemService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
