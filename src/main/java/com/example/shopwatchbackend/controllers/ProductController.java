package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.ProductDTO;
import com.example.shopwatchbackend.dtos.response.ProductListResponse;
import com.example.shopwatchbackend.dtos.response.ProductResponse;
import com.example.shopwatchbackend.services.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService iProductService;


    @GetMapping()
    public ResponseEntity<ProductListResponse> getAll(
            @RequestParam(defaultValue = "",name = "keyword") String keyword,
            @RequestParam(defaultValue = "0",name = "category_id") int categoryId,
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "12",name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "min_price") Double minPrice,
            @RequestParam(defaultValue = "0", name = "max_price") Double maxPrice
    ){
        PageRequest pageRequest = PageRequest.of(page,limit, Sort.by("productId").descending());
        Page<ProductResponse> productResponses = iProductService.getAll(keyword,categoryId,pageRequest,minPrice,maxPrice);
        int totalPages = productResponses.getTotalPages();
        List<ProductResponse> productResponseList = productResponses.getContent();
        return ResponseEntity.ok().body(ProductListResponse.builder().productResponses(productResponseList).totalPages(totalPages).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) throws Exception {
        try{
            ProductResponse productResponse = iProductService.getById(id);
            return ResponseEntity.ok().body(productResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getByIds(@RequestParam("ids") String ids){
        try{
            List<Integer> productIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
            List<ProductResponse> productResponses  = iProductService.getByIds(productIds);
            return ResponseEntity.ok().body(productResponses);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO){

        try{
            String result = iProductService.createProduct(productDTO);

            return ResponseEntity.ok(result);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable int id,@Valid @RequestBody ProductDTO productDTO) throws Exception {

        try{
            String product = iProductService.updateProduct(id,productDTO);
            return ResponseEntity.ok(product);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        String product = iProductService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }
}
