package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Long> {
    List<InvoiceDetail> findAllByInvoiceId(Long invoiceId);
}
