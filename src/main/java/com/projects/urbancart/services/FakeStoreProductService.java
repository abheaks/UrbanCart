package com.projects.urbancart.services;

import com.projects.urbancart.dtos.FakeProductDto;
import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Category;
import com.projects.urbancart.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {
    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Product convertFakeStoreDtoToProduct(FakeProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());

        Category category = new Category();
        category.setTitle(dto.getCategory());
        product.setCategory(category);

        return product;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {

        FakeProductDto fakeProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeProductDto.class);
        if (fakeProductDto == null) {
            throw new ProductNotFoundException(id, "Product with id " + id + " not found");
        }
        return convertFakeStoreDtoToProduct(fakeProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeProductDto[] fakeProductList = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeProductDto[].class);
        //generics will not have datatype in run time thus list won't work in the above case
        List<Product> response = new ArrayList<>();
        for (FakeProductDto fakeProductDto : fakeProductList) {
            response.add(convertFakeStoreDtoToProduct(fakeProductDto));
        }
        return response;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeProductDto fakeProductDto = new FakeProductDto();
        fakeProductDto.setTitle(product.getTitle());
        fakeProductDto.setImage(product.getImage());
        fakeProductDto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeProductDto, FakeProductDto.class);
        HttpMessageConverterExtractor<FakeProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeProductDto.class,
                        restTemplate.getMessageConverters());
        FakeProductDto response =
                restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreDtoToProduct(response);
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}
