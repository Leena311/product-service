package com.techie.microservices.product.controller;
import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/createproduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.savedProduct(productRequest);
    }

    @GetMapping("/allproduct")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
       return  productService.findAllProduct();
    }
    @GetMapping("/productbyId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable Integer id){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return productService.findProductById(id);
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Integer id,
                                         @RequestBody ProductRequest productRequest){
       return productService.updateProduct(id,productRequest);
    }
    @DeleteMapping("/deleteproduct/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Integer id){
         productService.deleteProductById(id);

    }



}
