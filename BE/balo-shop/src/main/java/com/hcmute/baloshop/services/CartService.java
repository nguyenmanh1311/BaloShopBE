package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<ResponseObject> getAllCart();
    ResponseEntity<ResponseObject> getCartById(Long id);
    ResponseEntity<ResponseObject> getCartByUserId(Long id);

    ResponseEntity<?> getCartByUserId(Long id, Boolean status);
    ResponseEntity<ResponseObject> createCart(CartRequestDTO cartRequestDTO);
    ResponseEntity<ResponseObject> updateCart(Long id, CartRequestDTO cartRequestDTO);
    ResponseEntity<ResponseObject> deleteCartById(Long id);
    ResponseEntity<ResponseObject> calTotalPrice(Long id);
}
