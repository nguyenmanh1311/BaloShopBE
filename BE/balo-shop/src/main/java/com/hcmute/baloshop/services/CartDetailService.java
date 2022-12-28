package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.dto.cartdetail.CartDetailChangeQuantityDto;
import com.hcmute.baloshop.dto.cartdetail.CartDetailRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CartDetailService {
    ResponseEntity<ResponseObject> getAllCartDetailByCartId(Long id);
    ResponseEntity<ResponseObject> getCartDetailById(Long id);
    ResponseEntity<ResponseObject> createCartDetail(CartDetailRequestDTO cartDetailRequestDTO);
    ResponseEntity<ResponseObject> updateQuantity(CartDetailChangeQuantityDto cartDetailChangeQuantityDto);
    ResponseEntity<ResponseObject> deleteCartDetailById(Long id);

}
