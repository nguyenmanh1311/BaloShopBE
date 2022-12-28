package com.hcmute.baloshop.repositories;


import com.hcmute.baloshop.entities.ImageProduct;
import com.hcmute.baloshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    List<ImageProduct> findAllByProductId(Long productId);
    Optional<ImageProduct> findAllByPathAndProductId(String path,Long productId);

}
