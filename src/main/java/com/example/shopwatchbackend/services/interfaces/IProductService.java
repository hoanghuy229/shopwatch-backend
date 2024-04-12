package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.ProductRequest;
import com.example.shopwatchbackend.dtos.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Page<ProductResponse> getAll(String keyword, int categoryId, PageRequest pageRequest,Double minPrice,Double maxPrice);
    ProductResponse getById(int id) throws Exception;
    String createProduct(ProductRequest productRequest) throws Exception;
    String updateProduct(int id, ProductRequest productRequest) throws Exception;
    String deleteProduct(int id);

    List<ProductResponse> getByIds(List<Integer> productIds);

    List<ProductResponse> getPopularProduct();

    String saveImageProduct(int id,String fileName) throws Exception;
}
