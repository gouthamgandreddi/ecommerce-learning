package com.enterprise.orderservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // Reverse navigation - many Categories contain many Products
    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();
}
