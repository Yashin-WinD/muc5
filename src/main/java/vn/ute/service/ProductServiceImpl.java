package vn.ute.service;

import vn.ute.dto.PageResponse;
import vn.ute.dto.ProductInput;
import vn.ute.entity.Category;
import vn.ute.entity.Product;
import vn.ute.repository.CategoryRepository;
import vn.ute.repository.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllSortedByPrice(String order) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productRepository.findAll(Sort.by(direction, "price"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByCategory(Long categoryId) {
        return findByCategory(categoryId, "ASC");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByCategory(Long categoryId, String order) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return productRepository.findByCategory_Id(categoryId, Sort.by(direction, "price"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product create(ProductInput input) {
        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + input.getCategoryId()));

        Product product = new Product();
        product.setTitle(input.getTitle());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setImages(input.getImages() != null && !input.getImages().isBlank()
                ? input.getImages()
                : "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=60");
        product.setAmount(input.getAmount() != null ? input.getAmount() : 0);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductInput input) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + input.getCategoryId()));

        product.setTitle(input.getTitle());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        if (input.getImages() != null && !input.getImages().isBlank()) {
            product.setImages(input.getImages());
        }
        product.setAmount(input.getAmount() != null ? input.getAmount() : 0);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Product> findPage(String search, Long categoryId, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(Math.max(0, page), size > 0 ? size : 6, sort);

        Page<Product> productPage;
        boolean hasSearch = search != null && !search.trim().isEmpty();
        boolean hasCategory = categoryId != null && categoryId > 0;

        if (hasSearch && hasCategory) {
            productPage = productRepository.findByTitleContainingIgnoreCaseAndCategory_Id(search.trim(), categoryId, pageable);
        } else if (hasSearch) {
            productPage = productRepository.findByTitleContainingIgnoreCase(search.trim(), pageable);
        } else if (hasCategory) {
            productPage = productRepository.findByCategory_Id(categoryId, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        return PageResponse.from(productPage);
    }
}
