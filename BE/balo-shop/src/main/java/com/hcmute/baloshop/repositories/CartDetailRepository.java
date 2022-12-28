package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Cart;
import com.hcmute.baloshop.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    List<CartDetail> findALLByCartId(Long id);
}
