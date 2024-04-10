package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.categoryId = :categoryId)"+
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword%)" +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.price BETWEEN :minPrice AND :maxPrice)")
    Page<Product> getAllProducts(String keyword, int categoryId, PageRequest pageRequest, Double minPrice, Double maxPrice);

    List<Product> findAllByProductIdIn(List<Integer> ids);


}
