package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.OrderDetailDTO;
import com.HutechB6.Ecommerce.model.OrderDetail;
import com.HutechB6.Ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailById(@PathVariable Long id) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetail(id);
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setId(orderDetail.getId());
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            orderDetailDTO.setProductId(orderDetail.getProduct().getId());
            orderDetailDTOs.add(orderDetailDTO);
        }

        return ResponseEntity.ok(orderDetailDTOs);
    }
}
