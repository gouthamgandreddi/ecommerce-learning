package com.enterprise.orderservice.controller;

import com.enterprise.orderservice.dto.CreateOrderRequest;
import com.enterprise.orderservice.dto.OrderResponse;
import com.enterprise.orderservice.dto.UpdateShippingStatusRequest;
import com.enterprise.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Place a new order",
            description = "Creates a new order from cart details and returns the created order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Malformed request body or missing required data")
    })
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(201).body(orderService.createOrder(request));
    }

    @Operation(summary = "Fetch an order by ID",
            description = "Extracts the orderId from the path and returns the matching order details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> fetchOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List orders (paginated)",
            description = "Returns a page of orders. Query params: page (0-indexed), size, sort")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of orders returned")
    })
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(pageable));
    }

    @Operation(summary = "Update an order's shipping status",
            description = "Partially updates only the latestStatus field of an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/orders/{orderId}/shipping-status")
    public ResponseEntity<OrderResponse> updateShippingStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateShippingStatusRequest request) {
        return ResponseEntity.ok(orderService.updateShippingStatus(orderId, request.getLatestStatus()));
    }

    @Operation(summary = "Delete an order",
            description = "Soft-deletes the order by setting deletedAt. The record is retained for audit purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
