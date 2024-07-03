package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.*;
import com.HutechB6.Ecommerce.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;  // Assuming you have a CartService
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private UserDetailsServiceImp userService;
    @Autowired
    private IUserRepository userRepository;
    @Transactional
    public Order createOrderSubmit(String customerName, String customerAddress, String customerPhone, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerPhone(customerPhone);

        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        // Optionally clear the cart after order placement
        cartItemRepository.clear();
        return order;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
