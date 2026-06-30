package com.enterprise.orderservice.service;

import com.enterprise.orderservice.model.Order;
import com.enterprise.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
