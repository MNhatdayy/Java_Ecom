package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.DTO.PaymentDTO;
import com.HutechB6.Ecommerce.response.ResponseObject;
import com.HutechB6.Ecommerce.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request, @RequestParam Long total) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, total));
    }
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if ("00".equals(status)) {
            // Payment successful, redirect to home page or confirmation page
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173/").body(null);
        } else {
            // Payment failed, handle accordingly
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173/error").body(null);
        }
    }
}
