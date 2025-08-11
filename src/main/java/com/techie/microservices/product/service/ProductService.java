package com.techie.microservices.product.service;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.entity.Product;
import com.techie.microservices.product.exception.ProductNotFoundException;
import com.techie.microservices.product.exception.ResourceNotFoundException;
import com.techie.microservices.product.mapper.ProductMapper;
import com.techie.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    @Autowired
     private ProductRepository productRepository;

    public ProductResponse savedProduct(ProductRequest productRequest){
        Product productEntity = ProductMapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(productEntity);
        log.info("Product Created Successful !!!");
        return ProductMapper.toResponse(savedProduct);

    }
    public List<ProductResponse> findAllProduct(){
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }
    public ProductResponse findProductById(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found With Given Id" + id));
        return ProductMapper.toResponse(product);
    }
    public ProductResponse updateProduct(Integer id,ProductRequest productRequest){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        Product saved = productRepository.save(product);
        return  new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice()
        );
    }
    public void deleteProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        Product product = optionalProduct.orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id)
        );

        productRepository.delete(product);
    }





}
