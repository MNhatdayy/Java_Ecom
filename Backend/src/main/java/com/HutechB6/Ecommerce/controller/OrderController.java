package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.OrderDetailDTO;
import com.HutechB6.Ecommerce.model.Order;
import com.HutechB6.Ecommerce.model.OrderDetail;
import com.HutechB6.Ecommerce.service.OrderDetailService;
import com.HutechB6.Ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

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

}