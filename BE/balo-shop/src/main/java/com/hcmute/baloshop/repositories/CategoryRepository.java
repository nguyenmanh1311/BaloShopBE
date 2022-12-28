package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Brand;
import com.hcmute.baloshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query(value = "SELECT * FROM tbl_category p WHERE p.is_delete = false ", nativeQuery = true)
    List<Category> findAllByIsDelete();
    @Query(value = "SELECT * FROM tbl_category p WHERE p.id = :id AND p.is_delete = false ", nativeQuery = true)
    List<Category> findAllByIdAndIsDelete(@Param("id")Long id);
}
