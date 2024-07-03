package com.HutechB6.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private Long productId;
}
