package com.techie.microservices.product.mapper;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.entity.Product;

public class ProductMapper {
    public  static ProductResponse toResponse(Product product){
        if(product==null){
            return  null;

        }
return   new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice()
);

    }
    public static Product toEntity(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        return  product;
    }


}
