package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value = "SELECT * FROM tbl_cart p WHERE p.status=false", nativeQuery = true)
    List<Cart> findCartOfUser(Long id);

    List<Cart> findCartByUserIdAndStatus(Long id,Boolean status);
    List<Cart> findAllByUserId(Long id);

    Optional<Cart> findAllById(Long id);
}
