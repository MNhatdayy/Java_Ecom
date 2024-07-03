package com.HutechB6.Ecommerce.DTO;

import lombok.*;

public abstract class PaymentDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VNPayResponse {
        private String code;
        private String message;
        private String paymentUrl;
    }
}
