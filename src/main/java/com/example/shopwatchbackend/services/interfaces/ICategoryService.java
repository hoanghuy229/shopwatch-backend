package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.CategoryRequest;
import com.example.shopwatchbackend.dtos.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse getCategoryById(int id) throws Exception;

    String createCategory(CategoryRequest categoryRequest) throws Exception;

    String updateCategory(int id, CategoryRequest categoryRequest) throws Exception;

    String deleteCategory(int id);
}
