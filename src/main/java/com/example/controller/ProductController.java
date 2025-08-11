package com.example.controller;

import com.example.dto.ProductRequestDto;
import com.example.dto.ProductResponseDto;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

     //create product
   @PostMapping("/createproduct")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto){
        return  new ResponseEntity<>(service.saveProduct(productRequestDto),HttpStatus.CREATED);
    }
    //getAllproduct
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAllProduct(){
       return  new ResponseEntity<>(service.getAllProduct(),HttpStatus.OK);
    }

    //updateproduct
    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto updatedProduct = service.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(updatedProduct);
    }

    //get product by id
    @GetMapping("/getproduct/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto productResponse = service.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }
    //delete product
    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
    }
}



