package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Address;
import com.hcmute.baloshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findProductByName(String name);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.name LIKE %:input% AND p.is_delete = false ", nativeQuery = true)
    List<Product> searchProductByKeyWord(@Param("input") String input);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.is_delete = false ORDER BY p.id DESC ", nativeQuery = true)
    List<Product> findAll();
    @Query(value = "SELECT * FROM tbl_product p WHERE p.branch_id = :id AND p.is_delete = false ", nativeQuery = true)
    List<Product> findByBranchId(@Param("id") Long id);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.category_id = :id AND p.is_delete = false ", nativeQuery = true)
    List<Product> findByCategoryId(@Param("id") Long id);

    @Query(value = "SELECT * FROM tbl_product p WHERE MONTH(p.created_date) = MONTH(NOW()) AND p.is_delete = false LIMIT 8", nativeQuery = true)
    List<Product> findTop8();
    List<Product> findAllByCategoryIdAndAndBranchId(Long CategoryId, Long BranchId);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.category_id = :categoryId AND p.is_delete = false LIMIT 4", nativeQuery = true)
    List<Product> findProductRelated(@Param("categoryId") long categoryId);

    @Query(value = "(SELECT b.product_id from (SELECT SUM(i.quantity) as total,product_id from tbl_invoice_detail i group by product_id order by total) as b LIMIT 8)", nativeQuery = true)
    List<Long> getTop8BestSelling();

    Optional<Product> findById(Long productId);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.category_id IN :categoryIds " +
            "AND p.branch_id IN :brandIds AND (p.price >= :minPrice AND p.price <= :maxPrice) AND p.is_delete = false", nativeQuery = true)
    List<Product> findAllByFilter(@Param("categoryIds") Long[] categoryIds,
                                  @Param("brandIds") Long[] brandIds,
                                  @Param("minPrice") Long minPrice,
                                  @Param("maxPrice") Long maxPrice);
    @Query(value = "SELECT * FROM tbl_product p WHERE p.id = :id AND p.is_delete = false ", nativeQuery = true)
    List<Product> findAllByIdAndIsDelete(@Param("id")Long id);
}
