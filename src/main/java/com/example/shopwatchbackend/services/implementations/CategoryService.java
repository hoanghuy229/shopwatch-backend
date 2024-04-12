package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.CategoryRequest;
import com.example.shopwatchbackend.models.Category;
import com.example.shopwatchbackend.repositories.CategoryRepository;
import com.example.shopwatchbackend.dtos.response.CategoryResponse;
import com.example.shopwatchbackend.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;



    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> modelMapper.map(category,CategoryResponse.class)).toList();
    }

    @Override
    public CategoryResponse getCategoryById(int id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new Exception("cannot find"));
        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    @Transactional
    public String createCategory(CategoryRequest categoryRequest) throws Exception {
        Category existedCategory = categoryRepository.findByName(categoryRequest.getName());
        if(existedCategory != null){
            throw new Exception("CATEGORY NAME EXISTS!!!");
        }
        Category newCategory = Category.builder().name(categoryRequest.getName()).build();
        categoryRepository.save(newCategory);
        return "add cate success";
    }

    @Override
    @Transactional
    public String updateCategory(int id, CategoryRequest categoryRequest) throws Exception {
        Category existCategory = categoryRepository.findById(id).orElseThrow(() -> new Exception("not found"));
        Category checkName = categoryRepository.findByName(categoryRequest.getName());
        if(checkName != null){
            throw new Exception("CATEGORY NAME EXISTS!!!");
        }
        existCategory.setName(categoryRequest.getName());
        categoryRepository.save(existCategory);
        return "update cate success";
    }

    @Override
    @Transactional
    public String deleteCategory(int id) {
        categoryRepository.deleteById(id);
        return "delete success";
    }
}
