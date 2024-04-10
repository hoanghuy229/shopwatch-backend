package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.ProductDTO;
import com.example.shopwatchbackend.dtos.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Page<ProductResponse> getAll(String keyword, int categoryId, PageRequest pageRequest,Double minPrice,Double maxPrice);
    ProductResponse getById(int id) throws Exception;
    String createProduct(ProductDTO productDTO) throws Exception;
    String updateProduct(int id,ProductDTO productDTO) throws Exception;
    String deleteProduct(int id);

    List<ProductResponse> getByIds(List<Integer> productIds);
}
