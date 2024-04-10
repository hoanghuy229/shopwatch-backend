package com.example.shopwatchbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList;
}
