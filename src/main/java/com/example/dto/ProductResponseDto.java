package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

//public record ProductResponseDto(Long id,String name,String prod_description,Double price,CategoryResponseDto category) {
//}
//public record ProductResponseDto(Long id
//        ,String name
//        ,String prod_description
//        ,Double price
//        ,SimpleCategoryDto category) {
//}
public record ProductResponseDto(
        Long id,
        String name,
        String prod_description,
        Double price,
        CategoryResponseDto category

) {}