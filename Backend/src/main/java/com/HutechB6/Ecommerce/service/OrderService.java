package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.CartItem;
import com.HutechB6.Ecommerce.model.Order;
import com.HutechB6.Ecommerce.model.OrderDetail;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.repository.IOrderDetailRepository;
import com.HutechB6.Ecommerce.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;  // Assuming you have a CartService
    @Autowired
    private PaymentService paymentService;
    @Transactional
    public Order createOrder(String customerName, String customerAddress, User currentUser, String customerPhone, long methodCheckout, List<CartItem> cartItems){
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setUser(currentUser);
        order.setCustomerPhone(customerPhone);

        order.setPayment(paymentService.getPaymentById(methodCheckout).orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + methodCheckout)));
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        cartService.clearCart();

        return order;
    }
}
