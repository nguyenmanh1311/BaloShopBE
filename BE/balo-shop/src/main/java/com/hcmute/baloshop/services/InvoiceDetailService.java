package com.hcmute.baloshop.services;

import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface InvoiceDetailService {
    ResponseEntity<ResponseObject> getByInvoiceId(Long invoiceId);
}
