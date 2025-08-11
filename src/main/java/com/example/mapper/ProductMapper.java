package com.example.mapper;

import com.example.dto.ProductRequestDto;
import com.example.dto.ProductResponseDto;

import com.example.entity.Product;

public class ProductMapper {
    //Dto->Entity
    public static  Product toProductEntity(ProductRequestDto productRequestDto){
        if(productRequestDto==null){
            return  null;
        }
        return Product.builder()
                .name(productRequestDto.name())
                .prod_description(productRequestDto.prod_description())
                .price(productRequestDto.price())
                .categoryId(productRequestDto.categoryId())
                .build();
    }
    //Entity to DTO
    public  static ProductResponseDto toProductDto(Product product, CategoryResponseDto categoryDto){
        if (product == null || categoryDto == null) {
            return null;
        }
        return  new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getProd_description(),
                product.getPrice(),
               new SimpleCategoryDto(categoryDto.id(),categoryDto.name(),categoryDto.createdDate())
               // new SimpleCategoryDto(categoryDto.id(),categoryDto.name(),categoryDto.createdDate())
               // new CategoryResponseDto(categoryDto.id(),categoryDto.name(),categoryDto.createdDate())


        );


    }


}
