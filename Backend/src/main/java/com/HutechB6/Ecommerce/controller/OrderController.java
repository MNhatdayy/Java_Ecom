package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.DTO.OrderCreateRequest;
import com.HutechB6.Ecommerce.DTO.PaymentDTO;
import com.HutechB6.Ecommerce.model.*;

import com.HutechB6.Ecommerce.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartItemService CartItemService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequest orderRequest) {
        try {
            // Log the incoming request
            System.out.println("Received order creation request: " + orderRequest);

            // Authenticate user from token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Log the username
            System.out.println("Authenticated username: " + username);

            // Find user by username (assuming the username is unique and represents the user's ID or unique identifier)
            User user = userDetailsServiceImp.findUserByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

            // Verify userId from request matches userId from token
            if (!user.getId().equals(orderRequest.getUserId())) {
                throw new IllegalStateException("User ID mismatch");
            }

            // Create Order object from request
            Order order = new Order();
            order.setCustomerName(orderRequest.getCustomerName());
            order.setCustomerAddress(orderRequest.getCustomerAddress());
            order.setCustomerPhone(orderRequest.getCustomerPhone());

            // Log before finding user
            System.out.println("Finding user with ID: " + orderRequest.getUserId());

            // Log before finding payment
            System.out.println("Finding payment with ID: " + orderRequest.getPaymentId());

            // Find payment method and verify existence
            Payment payment = paymentService.getPaymentById(orderRequest.getPaymentId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + orderRequest.getPaymentId()));

            // Set user and payment method for order
            order.setUser(user);
            order.setPayment(payment);

            // Log before creating order
            System.out.println("Creating order: " + order);

            // Create order
            Order createdOrder = orderService.createOrder(order);

            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            System.err.println("Illegal state: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/submit")
    public ResponseEntity<?> submitOrder(@RequestParam String customerName,
                                         @RequestParam String customerAddress,
                                         @RequestParam String customerPhone,
                                         @RequestParam String payment,
                                         HttpServletRequest request) throws ServletException, IOException {
        List<CartItem> cartItems = CartItemService.getCartItemsFull();
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart is empty");
        }
        if ("cod".equals(payment)) {
            orderService.createOrderSubmit(customerName, customerAddress, customerPhone, cartItems);// Assuming a method to clear the cart
            return ResponseEntity.ok("Order placed successfully (Cash on Delivery)");
        } else if ("vnpay".equals(payment)) {
            // Calculate total amount from cart items
            double total = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
            Long totalAmount = (long) (total * 100); // Convert to VNPay's required format

            // Create VNPay payment and retrieve payment URL
            PaymentDTO.VNPayResponse payResponse = paymentService.createVnPayPayment(request, totalAmount);
            String payUrl = payResponse.getPaymentUrl();
            // Optionally clear the cart after successful payment initiation

            // Return the payment URL to client
            return ResponseEntity.ok(payUrl);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment method");
    }
    @GetMapping("/confirmation")
    public ResponseEntity<String> paymentConfirmation(
            @RequestParam("vnp_ResponseCode") String responseCode) {
        if ("00".equals(responseCode)) {
            // Payment successful
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173/").body(null);
        } else {
            // Payment failed
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:5173/error").body(null);
        }
    }
}