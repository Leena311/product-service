package com.example.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="category-service")
public interface CategoryClient {
   /* Your product-service should only consume data from category-service,
   not create categories. So you can remove this:
    */
//    @PostMapping("/api/categories")
//    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryDto);
    @GetMapping("/api/categories/{id}")
    ResponseEntity<CategoryResponseDto> findCategoryByID(@PathVariable Long id);

    @GetMapping("/api/categories")
    ResponseEntity<List<CategoryResponseDto>> findAllCategory();

    @DeleteMapping("/api/categories/{id}")
    String deleteCategory(@PathVariable Long id);
}








