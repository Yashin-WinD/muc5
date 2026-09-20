package com.example.graphql.service;

import com.example.graphql.dto.PageResponse;
import com.example.graphql.dto.ProductInput;
import com.example.graphql.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllSortedByPrice(String order);
    List<Product> findByCategory(Long categoryId);
    List<Product> findByCategory(Long categoryId, String order);
    Optional<Product> findById(Long id);
    Product create(ProductInput input);
    Product update(Long id, ProductInput input);
    boolean delete(Long id);
    PageResponse<Product> findPage(String search, Long categoryId, int page, int size, String sortBy, String sortDir);
}
