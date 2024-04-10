package com.example.shopwatchbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "cus_id",nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "payment_id",nullable = false)
    private Payment payment;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetailList;
}
