package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.payment.MomoPayment;
import com.hcmute.baloshop.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/payment")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("momo")
    public ResponseEntity<?> getLinkPaymentMomo(@RequestBody MomoPayment request) {
        return paymentService.createNewPayment(request);
    }
}
