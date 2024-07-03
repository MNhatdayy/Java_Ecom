package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.model.Category;
import com.HutechB6.Ecommerce.model.Payment;
import com.HutechB6.Ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayment() {
        return paymentService.getAllPayments();
    }
}
