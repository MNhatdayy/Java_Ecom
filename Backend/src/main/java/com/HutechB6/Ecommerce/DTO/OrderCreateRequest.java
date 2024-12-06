package com.HutechB6.Ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private Long userId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private Long paymentId;
}
