package com.HutechB6.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequest {
    private String username;

    private int quantity;
}
