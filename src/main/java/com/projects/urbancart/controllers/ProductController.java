package com.projects.urbancart.controllers;

import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Product;
import com.projects.urbancart.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);
    }

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
}
