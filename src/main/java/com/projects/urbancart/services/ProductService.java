package com.projects.urbancart.services;

import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product replaceProduct(Long id, Product product);

    Product createProduct(Product product);
}
