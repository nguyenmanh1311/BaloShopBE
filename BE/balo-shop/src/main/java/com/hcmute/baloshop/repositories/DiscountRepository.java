package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
}
