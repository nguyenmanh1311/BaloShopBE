package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.InvoiceDetailRepository;
import com.hcmute.baloshop.services.InvoiceDetailService;
import com.hcmute.baloshop.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoice-detail")
@CrossOrigin("*")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getByInvoiceId(
            @PathVariable(name = "id") Long id) {
        return this.invoiceDetailService.getByInvoiceId(id);
    }
}
