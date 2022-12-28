package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.payment.MomoPayment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> createNewPayment(MomoPayment request);
}
