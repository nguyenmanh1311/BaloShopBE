package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoicesRepository extends JpaRepository<Invoices,Long> {
    List<Invoices> findAllByUserIdOrderByCreatedDate(Long UserId);

    List<Invoices> findTop8ByOrderByCreatedDate();

    List<Invoices> findAllByOrderByCreatedDate();
}
