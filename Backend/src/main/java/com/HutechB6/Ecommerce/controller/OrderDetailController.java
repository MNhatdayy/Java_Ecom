package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.DTO.OrderDetailDTO;
import com.HutechB6.Ecommerce.DTO.OrderDetailResponse;
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
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailById(@PathVariable Long id) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetail(id);
        List<OrderDetailResponse> orderDetailDTOs = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailResponse orderDetailDTO = new OrderDetailResponse();
            orderDetailDTO.setId(orderDetail.getId());
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            orderDetailDTO.setProduct(orderDetail.getProduct());
            orderDetailDTO.setOrderId(orderDetail.getOrder().getId());
            orderDetailDTOs.add(orderDetailDTO);
        }

        return ResponseEntity.ok(orderDetailDTOs);
    }
}
