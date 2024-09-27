package com.projects.urbancart.services;

import com.projects.urbancart.dtos.FakeProductDto;
import com.projects.urbancart.exceptions.ProductNotFoundException;
import com.projects.urbancart.models.Category;
import com.projects.urbancart.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class FakeStoreProductService implements ProductService {
    private final RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
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

        Product prod = (Product) redisTemplate.opsForHash().get("product", "product-" + id);
        if (prod != null) {
            return prod;
        }
        FakeProductDto fakeProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeProductDto.class);
        if (fakeProductDto == null) {
            throw new ProductNotFoundException(id, "Product with id " + id + " not found");
        }
        Product product = convertFakeStoreDtoToProduct(fakeProductDto);
        redisTemplate.opsForHash().put("product", "product-" + product.getId(), product);
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
