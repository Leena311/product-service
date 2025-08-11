package com.techie.microservices.product.repository;

import com.techie.microservices.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InterfaceAddress;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
