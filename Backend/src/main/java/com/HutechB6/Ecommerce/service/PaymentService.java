package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.DTO.PaymentDTO;
import com.HutechB6.Ecommerce.config.VNPayConfig;
import com.HutechB6.Ecommerce.model.Payment;
import com.HutechB6.Ecommerce.repository.IPaymentRepository;
import com.HutechB6.Ecommerce.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final VNPayConfig vnPayConfig;
    private final IPaymentRepository paymentRepository;
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request, Long total) {
        Long amount =  total * 100;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}
