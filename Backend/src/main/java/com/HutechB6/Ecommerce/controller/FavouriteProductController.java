package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.CartRequest;
import com.HutechB6.Ecommerce.DTO.LikeRequest;
import com.HutechB6.Ecommerce.model.FavouriteProduct;
import com.HutechB6.Ecommerce.service.FavouriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/favourite")
public class FavouriteProductController {
    @Autowired
    private FavouriteProductService favouriteProductService;
    @GetMapping("/user/{username}")
    public ResponseEntity<List<FavouriteProduct>> GetFavouriteProductByUser(@PathVariable String username){
        List<FavouriteProduct> fproducts = favouriteProductService.GetFavouriteProductByUser(username);
        return new ResponseEntity<>(fproducts, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<FavouriteProduct> LikeProduct(@RequestBody LikeRequest likeRequest){
        FavouriteProduct likedProduct = favouriteProductService.LikeProduct(likeRequest.getUsername(), likeRequest.getProductId());
        return new ResponseEntity<>(likedProduct, HttpStatus.CREATED);
    }
    @PostMapping("/unlike")
    public ResponseEntity<Integer> UnLikeProduct(@RequestBody LikeRequest likeRequest){

        int check = favouriteProductService.UnlikeProduct(likeRequest.getUsername(), likeRequest.getProductId());
        if(check == 1){
            return new ResponseEntity<>(1, HttpStatus.OK);

        }
        return new ResponseEntity<>(0, HttpStatus.OK);
    }
    @GetMapping("/check")
    public ResponseEntity<Integer> CheckLiked(@RequestParam String username, @RequestParam Long productId){
        int check = favouriteProductService.CheckLike(username, productId);

        if(check == 1){
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(0,HttpStatus.OK);
    }
}
