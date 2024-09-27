package com.projects.urbancart.controllers;

import com.projects.urbancart.commons.AuthCommons;
import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Product;
import com.projects.urbancart.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private AuthCommons authCommons;

    public ProductController(ProductService productService, AuthCommons authCommons) {
        this.productService = productService;
        this.authCommons = authCommons;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id
//            , @RequestHeader("auth") String authToken
    ) throws ProductNotFoundException {
//        UserDto userDto = authCommons.validateToken(authToken);
        ResponseEntity<Product> responseEntity;
//        if (userDto == null) {
//            responseEntity = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//            return responseEntity;
//        }
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
