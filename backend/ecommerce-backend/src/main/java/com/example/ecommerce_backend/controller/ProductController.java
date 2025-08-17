package com.example.ecommerce_backend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_backend.model.Product;
import com.example.ecommerce_backend.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
    // This class will handle product-related requests
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
    @GetMapping("/{id}")
    public Product getProducBYId(@PathVariable Long id){
        return productRepository.findById(id).orElse(null);
    }
    @PostMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setImageUrl(productDetails.getImageUrl());
            return productRepository.save(product);
        } else {
            return null; // or throw an exception
        }

    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return "Product deleted!";
        
    }




}
