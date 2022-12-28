package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.dto.invoice.InvoiceRequestDto;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.CartService;
import com.hcmute.baloshop.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoice")
@CrossOrigin("*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(value = "/{id}/address/{addressId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewInvoice(
            @PathVariable(name = "id") Long id, @PathVariable(name = "addressId") Long addressId) {
        return this.invoiceService.createNewInvoice(id,addressId);
    }

    @RequestMapping(value = "invoiceid/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getInvoiceById(@PathVariable("id") Long id) {
        return this.invoiceService.getInvoiceById(id);
    }
    @RequestMapping(value = "payment/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<ResponseObject> setPaymentStatus(@PathVariable("id") Long id) {
        return this.invoiceService.changeStatusPayment(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> updateStatusInvoice(@RequestBody InvoiceRequestDto invoiceRequestDto) {
        return this.invoiceService.updateStatusInvoice(invoiceRequestDto);
    }

    @RequestMapping(value = "{UserId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getInvoiceByUserId(
            @PathVariable(name = "UserId") Long UserId) {
        return this.invoiceService.findByUserId(UserId);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAll() {
        return this.invoiceService.findAll();
    }

    @RequestMapping(value = "top8nearest", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getTop8Nearest() {
        return this.invoiceService.getTop8InvoiceNearest();
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> cancelInvoice(@PathVariable("id") Long id) {
        return this.invoiceService.cancelInvoice(id);
    }
}
