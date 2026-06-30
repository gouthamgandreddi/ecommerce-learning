package com.enterprise.orderservice.service;

import com.enterprise.orderservice.dto.CreateOrderRequest;
import com.enterprise.orderservice.dto.OrderResponse;
import com.enterprise.orderservice.exception.OrderNotFoundException;
import com.enterprise.orderservice.model.Order;
import com.enterprise.orderservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setCustomerId(request.getCustomerId());
        newOrder.setCartId(request.getCartId());
        newOrder.setPurchaseTotal(request.getPurchaseTotal());
        // latestStatus and createdAt are set by @PrePersist - not touched here

        Order saved = orderRepository.save(newOrder);
        return mapToResponse(saved);
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return mapToResponse(order);
    }

    public Page<OrderResponse> getOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(this::mapToResponse); // converts content, copies paging metadata
    }

    public OrderResponse updateShippingStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setLatestStatus(newStatus);

        Order updated = orderRepository.save(order); // ID present -> JPA issues UPDATE, not INSERT
        return mapToResponse(updated);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setDeletedAt(LocalDateTime.now()); // soft delete - @Where filters it out from now on

        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setCartId(order.getCartId());
        response.setPurchaseTotal(order.getPurchaseTotal());
        response.setLatestStatus(order.getLatestStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
