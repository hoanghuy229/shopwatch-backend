package com.example.shopwatchbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderDetailId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
