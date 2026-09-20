package vn.ute.controller;

import vn.ute.dto.PageResponse;
import vn.ute.dto.ProductInput;
import vn.ute.entity.Product;
import vn.ute.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    @Autowired
    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // 1. Hiển thị tất cả product có price từ thấp đến cao (hoặc cao đến thấp)
    @QueryMapping
    public List<Product> productsSortedByPrice(@Argument String order) {
        String sortOrder = (order != null && !order.isBlank()) ? order : "ASC";
        return productService.findAllSortedByPrice(sortOrder);
    }

    // 2. Lấy tất cả product của 01 category (hỗ trợ sắp xếp giá)
    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId, @Argument String order) {
        String sortOrder = (order != null && !order.isBlank()) ? order : "ASC";
        return productService.findByCategory(categoryId, sortOrder);
    }

    @QueryMapping
    public List<Product> products() {
        return productService.findAll();
    }

    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    // 3. Tìm kiếm có phân trang trên bảng Product
    @QueryMapping
    public PageResponse<Product> productsPage(
            @Argument String search,
            @Argument Long categoryId,
            @Argument Integer page,
            @Argument Integer size,
            @Argument String sortBy,
            @Argument String sortDir
    ) {
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 6;
        String sortField = sortBy != null ? sortBy : "id";
        String sortDirection = sortDir != null ? sortDir : "DESC";

        return productService.findPage(search, categoryId, pageNum, pageSize, sortField, sortDirection);
    }

    // 4. Mutation CRUD Product
    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        return productService.create(input);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput input) {
        return productService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }
}
