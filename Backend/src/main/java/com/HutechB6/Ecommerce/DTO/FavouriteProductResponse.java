package com.HutechB6.Ecommerce.DTO;

import com.HutechB6.Ecommerce.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteProductResponse {
    private String username;
    private Long productId;

}
