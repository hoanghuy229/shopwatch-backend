package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.ProductDTO;
import com.example.shopwatchbackend.models.Category;
import com.example.shopwatchbackend.models.Product;
import com.example.shopwatchbackend.repositories.CategoryRepository;
import com.example.shopwatchbackend.repositories.ProductRepository;
import com.example.shopwatchbackend.dtos.response.ProductResponse;
import com.example.shopwatchbackend.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<ProductResponse> getAll(String keyword, int categoryId, PageRequest pageRequest, Double minPrice, Double maxPrice) {
        Page<Product> productPage;
        if(minPrice != 0 && maxPrice != 0){
            productPage = productRepository.getAllProducts(keyword, categoryId, pageRequest, minPrice, maxPrice);
        }
        else{
            productPage = productRepository.getAllProducts(keyword, categoryId, pageRequest, null, null);
        }
        return productPage.map(product -> modelMapper.map(product, ProductResponse.class));
    }

    @Override
    public ProductResponse getById(int id) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(() -> new Exception("cannot find"));
        ProductResponse productResponse = new ProductResponse();
        modelMapper.map(product,productResponse);
        productResponse.setCategoryId(product.getCategory().getCategoryId());
        return productResponse;
    }

    @Override
    @Transactional
    public String createProduct(ProductDTO productDTO) throws Exception {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new Exception("cannot find"));
        Product product = Product
                .builder()
                .name(productDTO.getName())
                .image(productDTO.getImage())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity())
                .category(category)
                .build();
        productRepository.save(product);
        return "create success";
    }

    @Override
    @Transactional
    public String updateProduct(int id, ProductDTO productDTO) throws Exception {
        Product existProduct = productRepository.findById(id).orElseThrow(() -> new Exception("cannot find"));
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new Exception("cannot find cate"));
        existProduct.setCategory(category);
        existProduct.setName(productDTO.getName());
        existProduct.setPrice(productDTO.getPrice());
        existProduct.setDescription(productDTO.getDescription());
        existProduct.setQuantity(productDTO.getQuantity());
        existProduct.setImage(productDTO.getImage());

        productRepository.save(existProduct);
        return "update success";
    }

    @Override
    @Transactional
    public String deleteProduct(int id) {
        productRepository.deleteById(id);
        return "Delete success";
    }

    @Override
    public List<ProductResponse> getByIds(List<Integer> productIds) {
        List<Product> productList = productRepository.findAllByProductIdIn(productIds);
        return productList.stream().map(product -> modelMapper.map(product,ProductResponse.class)).toList();
    }
}
