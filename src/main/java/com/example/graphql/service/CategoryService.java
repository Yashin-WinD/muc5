package com.example.graphql.service;

import com.example.graphql.dto.CategoryInput;
import com.example.graphql.dto.PageResponse;
import com.example.graphql.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category create(CategoryInput input);
    Category update(Long id, CategoryInput input);
    boolean delete(Long id);
    PageResponse<Category> findPage(String search, int page, int size, String sortBy, String sortDir);
}
