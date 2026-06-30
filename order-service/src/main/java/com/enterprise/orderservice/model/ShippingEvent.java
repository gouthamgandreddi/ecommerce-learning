package com.enterprise.orderservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipping_events")
@Getter
@Setter
public class ShippingEvent {

    @Id
    private UUID eventId;

    @Column(name = "order_id")
    private Long orderId;

    private String status;

    private LocalDateTime eventTimestamp;
}
