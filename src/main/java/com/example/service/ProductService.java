package com.example.service;

import com.example.client.CategoryClient;
import com.example.dto.ProductRequestDto;
import com.example.dto.ProductResponseDto;
import com.example.entity.Product;
import com.example.mapper.ProductMapper;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryClient categoryClient;

    public ProductResponseDto saveProduct(ProductRequestDto productRequestDto) {
        ResponseEntity<CategoryResponseDto> categoryResponse = categoryClient.findCategoryByID(productRequestDto.categoryId());
        if (categoryResponse == null || categoryResponse.getBody() == null) {
            throw new ProductNotFoundException("Category not found with id: " + productRequestDto.categoryId());
        }
        Product product = ProductMapper.toProductEntity(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toProductDto(savedProduct, categoryResponse.getBody());
    }

    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    CategoryResponseDto category = categoryClient.findCategoryByID(product.getCategoryId()).getBody();
                    return ProductMapper.toProductDto(product, category);
                })
                .toList();
    }
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        CategoryResponseDto category = categoryClient.findCategoryByID(product.getCategoryId())
                .getBody();

        return ProductMapper.toProductDto(product, category);
    }
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Validate category exists
        CategoryResponseDto categoryDto = categoryClient.findCategoryByID(productRequestDto.categoryId())
                .getBody();
        if (categoryDto == null) {
            throw new RuntimeException("Category not found with id: " + productRequestDto.categoryId());
        }

        // Update product fields
        existingProduct.setName(productRequestDto.name());
        existingProduct.setProd_description(productRequestDto.prod_description());
        existingProduct.setPrice(productRequestDto.price());
        existingProduct.setCategoryId(productRequestDto.categoryId());

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductMapper.toProductDto(updatedProduct, categoryDto);
    }
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.delete(product);
    }


}