package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Brand;
import com.hcmute.baloshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Long> {
    Optional<Brand> findByName(String name);

    @Query(value = "SELECT * FROM tbl_brand p WHERE p.is_delete = false ", nativeQuery = true)
    List<Brand> findAllByIsDelete();
    @Query(value = "SELECT * FROM tbl_brand p WHERE p.id = :id AND p.is_delete = false ", nativeQuery = true)
    List<Brand> findAllByIdAndIsDelete(@Param("id")Long id);
}
