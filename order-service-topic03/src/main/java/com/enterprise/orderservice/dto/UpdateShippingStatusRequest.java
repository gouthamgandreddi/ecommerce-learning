package com.enterprise.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateShippingStatusRequest {

    @NotNull(message = "latestStatus is required")
    private String latestStatus;
}
