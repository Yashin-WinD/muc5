package com.example.graphql.controller;

import com.example.graphql.dto.CategoryInput;
import com.example.graphql.dto.PageResponse;
import com.example.graphql.entity.Category;
import com.example.graphql.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryGraphQLController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryGraphQLController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @QueryMapping
    public Optional<Category> categoryById(@Argument Long id) {
        return categoryService.findById(id);
    }

    @QueryMapping
    public PageResponse<Category> categoriesPage(
            @Argument String search,
            @Argument Integer page,
            @Argument Integer size,
            @Argument String sortBy,
            @Argument String sortDir
    ) {
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 6;
        String sortField = sortBy != null ? sortBy : "id";
        String sortDirection = sortDir != null ? sortDir : "DESC";

        return categoryService.findPage(search, pageNum, pageSize, sortField, sortDirection);
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryInput input) {
        return categoryService.create(input);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput input) {
        return categoryService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return categoryService.delete(id);
    }
}
