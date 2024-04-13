package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.ProductRequest;
import com.example.shopwatchbackend.dtos.response.ProductListResponse;
import com.example.shopwatchbackend.dtos.response.ProductResponse;
import com.example.shopwatchbackend.models.Product;
import com.example.shopwatchbackend.services.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
            @RequestParam(defaultValue = "4",name = "limit") int limit,
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

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularProduct(){
        List<ProductResponse> productResponses = iProductService.getPopularProduct();
        return ResponseEntity.ok().body(productResponses);
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
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest){

        try{
            String result = iProductService.createProduct(productRequest);

            return ResponseEntity.ok(result);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> uploadImages(@PathVariable("id") int id,
                                          @ModelAttribute("file") MultipartFile file) throws Exception {

        ProductResponse product = iProductService.getById(id);

        if(file.getSize() > 10*1024*1024){
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("file to large");
        }
        if(!file.getContentType().startsWith("image/")){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("not image");
        }
        try{
            String fileName = storeFile(file);
            String result = iProductService.saveImageProduct(id,fileName);
            return ResponseEntity.ok().body(result);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getProductImage(@PathVariable String imageName){
        try{
            Path imagePath = Paths.get("images/"+imageName);
            UrlResource urlResource = new UrlResource(imagePath.toUri());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(urlResource);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable int id,@Valid @RequestBody ProductRequest productRequest) throws Exception {

        try{
            String product = iProductService.updateProduct(id, productRequest);
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

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path uploadDir = Paths.get("images");

        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(),fileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
