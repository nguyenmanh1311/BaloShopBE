package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.invoice.InvoiceRequestDto;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<ResponseObject> createNewInvoice(Long cartId,Long addressId);
    ResponseEntity<ResponseObject> cancelInvoice(Long id);
    ResponseEntity<ResponseObject> updateStatusInvoice(InvoiceRequestDto invoiceRequestDto);
    ResponseEntity<ResponseObject> findByUserId(Long userId);
    ResponseEntity<ResponseObject> findAll();
    ResponseEntity<ResponseObject> getTop8InvoiceNearest();

    ResponseEntity<ResponseObject> getInvoiceById(Long id);
    ResponseEntity<ResponseObject> changeStatusPayment(Long id);

}
