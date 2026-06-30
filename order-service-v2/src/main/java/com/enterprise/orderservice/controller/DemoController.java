package com.enterprise.orderservice.controller;

import com.enterprise.orderservice.model.Category;
import com.enterprise.orderservice.model.Product;
import com.enterprise.orderservice.model.Review;
import com.enterprise.orderservice.repository.CategoryRepository;
import com.enterprise.orderservice.repository.ProductRepository;
import com.enterprise.orderservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ReviewRepository reviewRepository;

    @PostConstruct
    public void seed() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Category sale = new Category();
        sale.setName("Sale Items");
        categoryRepository.save(sale);

        for (int i = 1; i <= 5; i++) {
            Product p = new Product();
            p.setName("Laptop Model " + i);
            p.setPrice(new BigDecimal("999.99"));
            p.setDescription("A reasonably priced laptop.");
            p.setCategories(List.of(electronics, sale));
            productRepository.save(p);

            for (int r = 1; r <= 3; r++) {
                Review review = new Review();
                review.setRating(r + 2);
                review.setComment("Review " + r + " for product " + i);
                review.setCreatedAt(LocalDateTime.now());
                review.setProduct(p);
                reviewRepository.save(review);
            }
        }
    }

    // Endpoint that triggers N+1 when fetching all products and their reviews
    @GetMapping("/products")
    public List<String> listProductsWithReviewCount() {
        List<Product> products = productRepository.findAll();
        List<String> result = new ArrayList<>();
        for (Product p : products) {
            int count = p.getReviews().size();  // <-- the lazy access that fires extra queries
            result.add(p.getName() + " has " + count + " reviews");
        }
        return result;
    }
}
