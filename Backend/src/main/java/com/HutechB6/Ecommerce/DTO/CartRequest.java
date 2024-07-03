package com.HutechB6.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private String username;
    private Long productId;
    private int quantity;
}
