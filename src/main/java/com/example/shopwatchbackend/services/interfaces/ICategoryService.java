package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.CategoryDTO;
import com.example.shopwatchbackend.dtos.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse getCategoryById(int id) throws Exception;

    String createCategory(CategoryDTO categoryDTO) throws Exception;

    String updateCategory(int id,CategoryDTO categoryDTO) throws Exception;

    String deleteCategory(int id);
}
