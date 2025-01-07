package com.HutechB6.Ecommerce.DTO;

import com.HutechB6.Ecommerce.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponse {
    private Long id;
    private int quantity;
    private Long orderId;
    private Product product;
}
