package vn.ute.service;

import vn.ute.dto.CategoryInput;
import vn.ute.dto.PageResponse;
import vn.ute.entity.Category;
import vn.ute.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category create(CategoryInput input) {
        Category category = new Category();
        category.setName(input.getName());
        category.setDescription(input.getDescription());
        category.setIcon(input.getIcon() != null && !input.getIcon().isBlank() ? input.getIcon() : "bi-tag");
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryInput input) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(input.getName());
        category.setDescription(input.getDescription());
        if (input.getIcon() != null && !input.getIcon().isBlank()) {
            category.setIcon(input.getIcon());
        }
        return categoryRepository.save(category);
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Category> findPage(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(Math.max(0, page), size > 0 ? size : 10, sort);

        Page<Category> categoryPage;
        if (search != null && !search.trim().isEmpty()) {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return PageResponse.from(categoryPage);
    }
}
