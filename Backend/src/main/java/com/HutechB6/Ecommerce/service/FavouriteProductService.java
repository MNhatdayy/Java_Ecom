package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.FavouriteProduct;
import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.repository.IFavouriteProductRepository;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import com.HutechB6.Ecommerce.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavouriteProductService {
    @Autowired
    private IFavouriteProductRepository favouriteProductRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    public List<FavouriteProduct> GetFavouriteProductByUser (String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return favouriteProductRepository.findByUser(user);

    }
    public FavouriteProduct LikeProduct (String username, long productId){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        FavouriteProduct likedProduct = new FavouriteProduct();
        likedProduct.setCreatedDate(LocalDate.now());
        likedProduct.setUser(user);
        likedProduct.setProduct(product);
        return favouriteProductRepository.save(likedProduct);
    }
    public int UnlikeProduct (String username, long productId){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        List<FavouriteProduct> temp = favouriteProductRepository.findByUser(user);
        for(FavouriteProduct f : temp){
            if(f.getProduct().equals(product)){
                favouriteProductRepository.deleteById(f.getId());
                return 1;
            }


        }
        return 0;
    }
    public int CheckLike(String username, long productId){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        List<FavouriteProduct> temp = favouriteProductRepository.findByUser(user);
        for(FavouriteProduct f : temp){
            if(f.getProduct().equals(product))
                return 1;
        }
        return 0;
    }
}
