package vn.ute.repository;

import vn.ute.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 1. Hiển thị tất cả product sắp xếp theo giá
    List<Product> findAll(Sort sort);

    // 2. Lấy tất cả product của 01 category
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByCategory_Id(Long categoryId, Sort sort);
    List<Product> findByCategory_IdOrderByPriceAsc(Long categoryId);

    // 3. Phân trang và tìm kiếm theo tên
    Page<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // 4. Phân trang, tìm kiếm theo tên và lọc theo category
    Page<Product> findByTitleContainingIgnoreCaseAndCategory_Id(String title, Long categoryId, Pageable pageable);

    // 5. Phân trang theo category
    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);
}
