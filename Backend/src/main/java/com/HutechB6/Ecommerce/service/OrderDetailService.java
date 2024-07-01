package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.OrderDetail;
import com.HutechB6.Ecommerce.repository.IOrderDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    public List<OrderDetail> getOrderDetail(Long idOrder) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        for (OrderDetail orderDetail : orderDetails) {
            if(orderDetail.getOrder().getId().equals(idOrder)) {
                orderDetailList.add(orderDetail);
            }
        }
        return orderDetailList;
    }
}
