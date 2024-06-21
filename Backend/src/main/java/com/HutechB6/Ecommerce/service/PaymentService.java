package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.Payment;
import com.HutechB6.Ecommerce.repository.IPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final IPaymentRepository paymentRepository;
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}
