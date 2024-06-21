package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.service.CartService;
import com.HutechB6.Ecommerce.service.ProductImagesService;
import com.HutechB6.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImagesService productImageService;
    @GetMapping
    public String showCart(Model model) {
        List<CartItem> cartItemList = cartService.getCartItems();
        double total = 0;
        for(CartItem item : cartItemList){
            total = total + item.getProduct().getPrice()*item.getQuantity();
        }
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", total);
        return "/cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, RedirectAttributes redirectAttributes){
        int realQuantity = productService.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:")).getQuantity();
        List<CartItem> cartItems = cartService.getCartItems();
        CartItem item = new CartItem(productService.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:")), quantity);

        int temp = 0;
        if (!cartItems.contains(item)){
            if(realQuantity < quantity){

                redirectAttributes.addAttribute("error", "Số lượng không đủ");
                return "redirect:/products/" + productId;
            }

        }

        else{
            for(CartItem i : cartItems){
                if(i.getProduct().getId().equals(productId))

                    if(realQuantity < (i.getQuantity()+quantity)){

                        redirectAttributes.addAttribute("error", "Số lượng không đủ");
                        return "redirect:/products/" + productId;
                    }
            }
        }

        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
    @PostMapping("/update-cart")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestBody CartItem request) {
        Map<String, Object> response = new HashMap<>();
        try {
            cartService.updateCart(request.getProduct().getId(), request.getQuantity());
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
}
