package com.projects.urbancart.services;

import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Category;
import com.projects.urbancart.models.Product;
import com.projects.urbancart.repositories.CategoryRepository;
import com.projects.urbancart.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();

        if (category.getId() == null) {
            //we need to save the category
            Category savedCategory = categoryRepository.save(category);
            product.setCategory(savedCategory);
        } else {
            //we should check if the category id is valid or not.
//            Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getId());
//            Category category1 = optionalCategory.get();
//            product.setCategory(category1);
        }

        Product savedProduct = productRepository.save(product);
//
        return savedProduct;
    }
}
