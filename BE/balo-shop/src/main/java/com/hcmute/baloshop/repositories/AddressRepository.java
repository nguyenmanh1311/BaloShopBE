package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Address;
import com.hcmute.baloshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUserId(Long userId);
    Optional<Address> findTopByUserId(Long userId);
    @Query(value = "SELECT * FROM tbl_address a WHERE a.id = :id AND a.is_delete = false ", nativeQuery = true)
    List<Address> findAllByIdAndIsDelete(@Param("id")Long id);
    @Query(value = "SELECT * FROM tbl_address a WHERE a.user_id = :id AND a.is_delete = false ", nativeQuery = true)
    List<Address> findAllByUserIdAndIsDelete(@Param("id")Long id);
    Optional<Address> findById(Long id);
}
