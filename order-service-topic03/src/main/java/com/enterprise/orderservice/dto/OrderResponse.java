package com.enterprise.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String customerId;
    private UUID cartId;
    private BigDecimal purchaseTotal;
    private String latestStatus;
    private LocalDateTime createdAt;
}
