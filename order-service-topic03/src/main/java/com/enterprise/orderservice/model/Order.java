package com.enterprise.orderservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Where(clause = "deleted_at IS NULL") // every query JPA generates auto-filters soft-deleted rows
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @Column(name = "purchase_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchaseTotal;

    @Column(name = "latest_status", nullable = false)
    private String latestStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.latestStatus = "PLACED";
    }
}
