package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.CategoryDTO;
import com.example.shopwatchbackend.dtos.response.CategoryResponse;
import com.example.shopwatchbackend.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService iCategoryService;


    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<CategoryResponse> categoryResponses = iCategoryService.getAll();
        return ResponseEntity.ok(categoryResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) throws Exception {
        try{
            CategoryResponse categoryResponse = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
        try{
            String result = iCategoryService.createCategory(categoryDTO);
            return ResponseEntity.ok(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
       try{
           String result = iCategoryService.updateCategory(id,categoryDTO);
           return ResponseEntity.ok(result);
       }
       catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        String result = iCategoryService.deleteCategory(id);
        return ResponseEntity.ok(result);
    }

}
