package com.enterprise.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull(message = "customerId is required")
    private String customerId;

    @NotNull(message = "cartId is required")
    private UUID cartId;

    @NotNull(message = "purchaseTotal is required")
    @Positive(message = "purchaseTotal must be greater than 0")
    private BigDecimal purchaseTotal;
}
