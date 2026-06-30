package com.enterprise.orderservice.repository;

import com.enterprise.orderservice.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // findById, save, etc. inherited for free.
    Page<Order> findAll(Pageable pageable);
}
