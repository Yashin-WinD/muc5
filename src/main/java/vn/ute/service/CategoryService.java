package vn.ute.service;

import vn.ute.dto.CategoryInput;
import vn.ute.dto.PageResponse;
import vn.ute.entity.Category;

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
